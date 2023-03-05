package com.akshay.checkout.Service.impl;

import com.akshay.checkout.Constants.ApplicationConstants;
import com.akshay.checkout.Models.CheckoutModel;
import com.akshay.checkout.Service.CheckoutService;
import com.akshay.checkout.Service.pojoConverters.PojoConverter;
import com.akshay.checkout.repository.CheckoutRepository;
import com.akshay.common.utils.CommonLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
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
    private CommonLogger commonLogger;

    @Value("#{'${abandon.checkout.notification.minutes.gap}'.split(',')}")
    private List<Integer> notificationMinutesGap;

    @Value("#{'${abandon.checkout.notification.medium}'.split(',')}")
    private List<String> notificationMediums;

    @Autowired
    private CheckoutRepository checkoutRepository;

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

    private CheckoutModel getCheckoutPojo(String partner, String data){
        PojoConverter pojoConverter = null;
        if(partner.equalsIgnoreCase(ApplicationConstants.SHOPIFY)){
            pojoConverter = (PojoConverter) context.getBean(ApplicationConstants.SHOPIFY_POJO_CONVERTER_BEAN_NAME);
        }
        if(Objects.isNull(pojoConverter)){
            commonLogger.logInfo(String.format("Unknown partner received %s, message: %s", partner, data));
            return null;
        }
        return pojoConverter.getCheckoutPojo(data);
    }
}
