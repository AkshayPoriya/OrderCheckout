package com.akshay.webhookhandler.service.impl;

import com.akshay.checkout.Service.CheckoutService;
import com.akshay.checkout.Service.OrderService;
import com.akshay.common.dto.response.Response;
import com.akshay.common.utils.CommonLogger;
import com.akshay.webhookhandler.service.EventReceiverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventReceiverServiceImpl implements EventReceiverService {

    @Autowired
    private CommonLogger logger;

    @Autowired
    private CheckoutService checkoutService;

    @Autowired
    private OrderService orderService;

    @Override
    public Response recordOrderEvent(String partner, String msg) {
        logger.logInfo(String.format("Order Create Webhook for %s: %s", partner, msg));
        orderService.CreateOrder(partner, msg);
        return Response.builder()
                .ok(Boolean.TRUE)
                .code("200")
                .result("Order Event Received Successfully!")
                .build();
    }

    @Override
    public Response recordAbandonCheckoutEvent(String partner, String msg) {
        logger.logInfo(String.format("Abandon Checkout Webhook for %s: %s", partner, msg));
        checkoutService.CreateCheckout(partner, msg);
        return Response.builder()
                .ok(Boolean.TRUE)
                .code("200")
                .result("Abandon Checkout Event Received Successfully!")
                .build();
    }
}
