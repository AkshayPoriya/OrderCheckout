package com.akshay.checkout.Constants;

import java.util.List;

public class ApplicationConstants {
    public static final String CHECKOUT_COLLECTION_NAME = "checkout";

    public static final String ORDER_COLLECTION_NAME = "order_details";

    public static final String SHOPIFY_POJO_CONVERTER_BEAN_NAME = "shopify_pojo_converter";

    public static final String SHOPIFY = "SHOPIFY";

//    public static final List<Integer> ABANDON_CHECKOUT_NOTIFICATION_MINUTES = List.of(30, 1440, 2880, 4320);
//
//    public static final List<String> ABANDON_CHECKOUT_NOTIFICATION_MEDIUM = List.of("SMS", "WHATSAPP", "EMAIL");

    public static final String CHECKOUT_MONGO_TEMPLATE_BEAN_NAME = "checkout-mongo";

    public static final String BASE_URI = "/api/v1/checkout";

    public static final String ABANDON_NOTIFICATION_END_POINT = "/send-abandon-notification/";

}
