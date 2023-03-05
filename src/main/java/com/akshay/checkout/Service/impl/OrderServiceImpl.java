package com.akshay.checkout.Service.impl;

import com.akshay.checkout.Constants.ApplicationConstants;
import com.akshay.checkout.Models.OrderModel;
import com.akshay.checkout.Service.OrderService;
import com.akshay.checkout.Service.pojoConverters.PojoConverter;
import com.akshay.checkout.repository.CheckoutRepository;
import com.akshay.checkout.repository.OrderRepository;
import com.akshay.common.utils.CommonLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private CommonLogger commonLogger;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CheckoutRepository checkoutRepository;

    @Override
    public void CreateOrder(String partner, String data) {
        OrderModel orderModel = getOrderPojo(partner, data);
        if(Objects.isNull(orderModel)){
            return;
        }
        LocalDateTime currTime = LocalDateTime.now();
        orderModel.setCreatedAt(currTime);
        checkoutRepository.setCheckoutComplete(orderModel.getCheckoutId());
        orderRepository.saveOrderDetails(orderModel);
    }

    private OrderModel getOrderPojo(String partner, String data){
        PojoConverter pojoConverter = null;
        if(partner.equalsIgnoreCase(ApplicationConstants.SHOPIFY)){
            pojoConverter = (PojoConverter) context.getBean(ApplicationConstants.SHOPIFY_POJO_CONVERTER_BEAN_NAME);
        }
        if(Objects.isNull(pojoConverter)){
            commonLogger.logInfo(String.format("Unknown partner received %s, message: %s", partner, data));
            return null;
        }
        return pojoConverter.getOrderPojo(data);
    }
}
