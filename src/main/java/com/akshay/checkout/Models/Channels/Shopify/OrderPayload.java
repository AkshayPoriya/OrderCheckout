package com.akshay.checkout.Models.Channels.Shopify;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OrderPayload implements Serializable {

    @JsonProperty("order")
    private Order order;

    @Data
    public static class Order implements Serializable{

        @JsonProperty("checkout_id")
        private String checkoutId;

        @JsonProperty("id")
        private String orderId;

        @JsonProperty("order_status_url")
        private String orderStatusUrl;

        @JsonProperty("fulfillments")
        private List<fullfillment> fullfillmentList;

        @Data
        public static class fullfillment implements Serializable{

            @JsonProperty("tracking_url")
            private String trackingUrl;
        }
    }
}
