package com.akshay.webhookhandler.constants;

public interface ControllerUri {
    String BASE_URL = "/api/v1/webhook";

    interface CHECKOUT_CONTROLLER{
        String ORDER_END_POINT=  "/create-order/{PARTNER}/";
        String ABANDON_CHECKOUT_END_POINT=  "/abandon-checkout/{PARTNER}/";
    }
}
