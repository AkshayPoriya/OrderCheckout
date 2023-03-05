package com.akshay.checkout.Models;

import com.akshay.checkout.Constants.ApplicationConstants;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Document(collection = ApplicationConstants.CHECKOUT_COLLECTION_NAME)
public class CheckoutModel {

    @Field(value = "checkout_id", write = Field.Write.ALWAYS)
    private String checkoutId;

//    @Field(value = "order_id", write = Field.Write.ALWAYS)
//    private String orderId;

    @Field(value = "created_at", write = Field.Write.ALWAYS)
    private LocalDateTime createdAt;

    @Field(value = "updated_at", write = Field.Write.ALWAYS)
    private LocalDateTime updatedAt;

    @Field(value = "is_checkout_completed", write = Field.Write.ALWAYS)
    private Boolean isCheckoutCompleted;

    @Field(value = "customer_email", write = Field.Write.ALWAYS)
    private String customerEmail;

    @Field(value = "customer_first_name", write = Field.Write.ALWAYS)
    private String customerFirstName;

    @Field(value = "customer_last_name", write = Field.Write.ALWAYS)
    private String customerLastName;

    @Field(value = "customer_phone_no", write = Field.Write.ALWAYS)
    private String customerPhoneNo;

    @Field(value = "checkout_url", write = Field.Write.ALWAYS)
    private String checkoutUrl;

    @Field(value = "abandon_notification_details", write = Field.Write.ALWAYS)
    private List<NotificationDetail> abandonNotificationDetails;

    @Field(value = "next_notification_time", write = Field.Write.ALWAYS)
    private LocalDateTime nextNotificationTime;

    @Field(value = "next_notification_index", write = Field.Write.ALWAYS)
    private Integer nextNotificationIndex;

    @Data
    @Builder
    public static class NotificationDetail {

        @Field(value = "notification_time", write = Field.Write.ALWAYS)
        private LocalDateTime notificationTime;

        @Field(value = "notification_medium", write = Field.Write.ALWAYS)
        private List<String> notificationMedium;
    }

}
