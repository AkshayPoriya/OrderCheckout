package com.akshay.checkout.Constants;

public class MongoKeyConstants {

    public static class CHECKOUT{
        public static final String CHECKOUT_ID_KEY = "checkout_id";
        public static final String ORDER_ID_KEY = "order_id";
        public static final String CREATED_AT_KEY = "created_at";
        public static final String UPDATED_AT_KEY = "updated_at";
        public static final String IS_CHECKOUT_COMPLETE_KEY = "is_checkout_completed";
        public static final String CUSTOMER_EMAIL_KEY = "customer_email";
        public static final String CUSTOMER_FIRST_NAME_KEY = "customer_first_name";
        public static final String CUSTOMER_LAST_NAME_KEY = "customer_last_name";
        public static final String CUSTOMER_PHONE_NUMBER_KEY = "customer_phone_no";
        public static final String CHECKOUT_URL_KEY = "checkout_url";
        public static final String ABANDON_NOTIFICATION_DETAILS_KEY = "abandon_notification_details";
        public static final String NEXT_NOTIFICATION_TIME_KEY = "next_notification_time";
        public static final String NEXT_NOTIFICATION_INDEX_KEY = "next_notification_index";
        public static final String NOTIFICATION_TIME_KEY = "notification_time";
        public static final String NOTIFICATION_MEDIUM_KEY = "notification_medium";
    }

    public static class ORDER{
        public static final String CHECKOUT_ID_KEY = "checkout_id";
        public static final String ORDER_ID_KEY = "order_id";
        public static final String CREATED_AT_KEY = "created_at";
        public static final String ORDER_STATUS_URL_KEY = "order_status_url";
        public static final String TRACKING_URL_KEY = "tracking_url";
    }
}