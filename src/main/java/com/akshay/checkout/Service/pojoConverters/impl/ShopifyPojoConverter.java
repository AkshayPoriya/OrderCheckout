package com.akshay.checkout.Service.pojoConverters.impl;

import com.akshay.checkout.Constants.ApplicationConstants;
import com.akshay.checkout.Models.Channels.Shopify.CheckoutPayload;
import com.akshay.checkout.Models.Channels.Shopify.OrderPayload;
import com.akshay.checkout.Models.CheckoutModel;
import com.akshay.checkout.Models.OrderModel;
import com.akshay.checkout.Service.pojoConverters.PojoConverter;
import com.akshay.common.utils.CommonLogger;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;


@Component(ApplicationConstants.SHOPIFY_POJO_CONVERTER_BEAN_NAME)
public class ShopifyPojoConverter implements PojoConverter {

    @Autowired
    private CommonLogger commonLogger;

    @Override
    public CheckoutModel getCheckoutPojo(String message) {
        try{
            ObjectMapper oMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            CheckoutPayload checkoutPayload = oMapper.readValue(message, CheckoutPayload.class);
            CheckoutModel checkoutModel =  CheckoutModel.builder()
                    .checkoutId(checkoutPayload.getCheckoutId()+"_"+"Shopify") // to avoid duplicates
                    //.orderId(checkoutPayload.getOrderId()+"_"+"Shopify")
                    .checkoutUrl(checkoutPayload.getCheckoutUrl())
                    .build();

            if(!ObjectUtils.isEmpty(checkoutPayload.getCustomerDetails())){
                checkoutModel.setCustomerEmail(checkoutPayload.getCustomerDetails().getEmail());
                checkoutModel.setCustomerFirstName(checkoutPayload.getCustomerDetails().getFirstName());
                checkoutModel.setCustomerLastName(checkoutPayload.getCustomerDetails().getLastName());
            }
            if(!ObjectUtils.isEmpty(checkoutPayload.getPhone())){
                checkoutModel.setCustomerPhoneNo(checkoutPayload.getPhone().getPhone());
            }
            return checkoutModel;
        }catch (Exception ex){
            commonLogger.logError("Error in converting checkout pojo for shopify", ex);
        }
        return null;
    }

    @Override
    public OrderModel getOrderPojo(String message) {
        try{
            ObjectMapper oMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            OrderPayload orderPayload = oMapper.readValue(message, OrderPayload.class);
            OrderModel orderModel = OrderModel.builder()
                    .checkoutId(orderPayload.getOrder().getCheckoutId()+"_"+"Shopify")
                    .orderId(orderPayload.getOrder().getOrderId()+"_"+"Shopify")
                    .orderStatusUrl(orderPayload.getOrder().getOrderStatusUrl())
                    .trackingUrl(ObjectUtils.isEmpty(orderPayload.getOrder().getFullfillmentList()) ? null :
                            orderPayload.getOrder().getFullfillmentList().get(0).getTrackingUrl())
                    .build();
            return orderModel;
        }catch (Exception ex){
            commonLogger.logError("Error in converting order pojo for shopify", ex);
        }
        return null;
    }
}
