package com.akshay.checkout.Service.impl;

import com.akshay.checkout.Constants.ApplicationConstants;
import com.akshay.checkout.Models.CheckoutModel;
import com.akshay.checkout.Service.CheckoutService;
import com.akshay.checkout.Service.pojoConverters.PojoConverter;
import com.akshay.checkout.repository.CheckoutRepository;
import com.akshay.common.utils.CommonLogger;
import com.akshay.connect.Models.CommunicationMessage;
import com.akshay.connect.service.CommunicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private CommonLogger logger;

    @Value("#{'${abandon.checkout.notification.minutes.gap}'.split(',')}")
    private List<Integer> notificationMinutesGap;

    @Value("#{'${abandon.checkout.notification.medium}'.split(',')}")
    private List<String> notificationMediums;

    @Autowired
    private CheckoutRepository checkoutRepository;

    @Autowired
    private CommunicationService communicationService;

    @Override
    public void CreateCheckout(String partner, String data) {
        CheckoutModel checkoutModel = getCheckoutPojo(partner, data);
        if(Objects.isNull(checkoutModel)){
            return;
        }
        LocalDateTime currTime = LocalDateTime.now();
        checkoutModel.setCreatedAt(currTime);
        checkoutModel.setUpdatedAt(currTime);
        checkoutModel.setIsCheckoutCompleted(Boolean.FALSE);
        List<CheckoutModel.NotificationDetail> abandonNotificationDetails = new ArrayList<>();
        for(long minuteGap: notificationMinutesGap){
            abandonNotificationDetails.add(CheckoutModel.NotificationDetail.builder()
                            .notificationMedium(notificationMediums)
                            .notificationTime(currTime.plusMinutes(minuteGap))
                    .build());
        }
        checkoutModel.setAbandonNotificationDetails(abandonNotificationDetails);
        checkoutModel.setNextNotificationIndex(0);
        if(!ObjectUtils.isEmpty(abandonNotificationDetails)){
            checkoutModel.setNextNotificationTime(abandonNotificationDetails.get(0).getNotificationTime());
        }else{
            checkoutModel.setNextNotificationTime(null);
        }
        checkoutRepository.saveCheckoutObj(checkoutModel);
    }

    @Override
    @Scheduled(fixedRate = ApplicationConstants.ABANDON_NOTIFICATION_CRON_FREQUENCY)
    public void SendAbandonNotification() {
        logger.logInfo(String.format("SendAbandonNotification task started at %s", LocalDateTime.now()));
        List<CheckoutModel> checkoutModelList = checkoutRepository.getAbandonCheckoutOrdersForNotifications();
        for(CheckoutModel checkoutModel: checkoutModelList){
            String message = String.format("Hi %s, We noticed you left something in your cart, click %s to complete your order. Ignore if already done.",
                    checkoutModel.getCustomerFirstName(), checkoutModel.getCheckoutUrl());
            CommunicationMessage communicationMessage = CommunicationMessage.builder()
                    .body(message)
                    .medium(null)
                    .email(checkoutModel.getCustomerEmail())
                    .mobileNo(checkoutModel.getCustomerPhoneNo())
                    .maxRetryCount(1)
                    .maxRetryTime(null)
                    .build();

            List<CheckoutModel.NotificationDetail> notificationDetailList = checkoutModel.getAbandonNotificationDetails();
            Integer nextNotificationIndex = checkoutModel.getNextNotificationIndex();
            if(Objects.isNull(nextNotificationIndex) || notificationDetailList.size()<=nextNotificationIndex){
                checkoutModel.setNextNotificationTime(null);
                checkoutModel.setNextNotificationIndex(null);
                checkoutRepository.updateAbandonCheckoutNotificationDetails(checkoutModel);
                continue;
            }
            List<String> notificationMediums = notificationDetailList.get(nextNotificationIndex).getNotificationMedium();
            for(String medium: notificationMediums){
                communicationMessage.setMedium(medium);
                communicationService.sendCommunication(communicationMessage);
            }
            nextNotificationIndex++;
            if(nextNotificationIndex<notificationDetailList.size()){
                checkoutModel.setNextNotificationTime(notificationDetailList.get(nextNotificationIndex).getNotificationTime());
                checkoutModel.setNextNotificationIndex(nextNotificationIndex);
            }else{
                checkoutModel.setNextNotificationIndex(null);
                checkoutModel.setNextNotificationTime(null);
                logger.logInfo(String.format("All abandon notification sent for checkout id: %s", checkoutModel.getCheckoutId()));
            }
            checkoutRepository.updateAbandonCheckoutNotificationDetails(checkoutModel);
        }
        logger.logInfo(String.format("SendAbandonNotification task completed at %s", LocalDateTime.now()));
    }

    private CheckoutModel getCheckoutPojo(String partner, String data){
        PojoConverter pojoConverter = null;
        if(partner.equalsIgnoreCase(ApplicationConstants.SHOPIFY)){
            pojoConverter = (PojoConverter) context.getBean(ApplicationConstants.SHOPIFY_POJO_CONVERTER_BEAN_NAME);
        }
        if(Objects.isNull(pojoConverter)){
            logger.logInfo(String.format("Unknown partner received %s, message: %s", partner, data));
            return null;
        }
        return pojoConverter.getCheckoutPojo(data);
    }
}
