package com.akshay.checkout.Models.Channels.Shopify;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutPayload implements Serializable {

    @JsonProperty("id")
    private String checkoutId;

//    @JsonProperty("order_id")
//    private String orderId;

    @JsonProperty("customer")
    private CustomerDetails CustomerDetails;

    @JsonProperty("phone")
    private Phone phone;

    @JsonProperty("abandoned_checkout_url")
    private String checkoutUrl;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CustomerDetails{

        @JsonProperty("email")
        private String email;

        @JsonProperty("first_name")
        private String firstName;

        @JsonProperty("last_name")
        private String lastName;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Phone{
        @JsonProperty("phone")
        private String phone;
    }

}