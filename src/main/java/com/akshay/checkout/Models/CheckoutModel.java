package com.akshay.checkout.Models;

import com.akshay.checkout.Constants.ApplicationConstants;
import com.akshay.checkout.Constants.MongoKeyConstants;
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

    @Field(value = MongoKeyConstants.CHECKOUT.CHECKOUT_ID_KEY, write = Field.Write.ALWAYS)
    private String checkoutId;

//    @Field(value = MongoKeyConstants.CHECKOUT.ORDER_ID_KEY, write = Field.Write.ALWAYS)
//    private String orderId;

    @Field(value = MongoKeyConstants.CHECKOUT.CREATED_AT_KEY, write = Field.Write.ALWAYS)
    private LocalDateTime createdAt;

    @Field(value = MongoKeyConstants.CHECKOUT.UPDATED_AT_KEY, write = Field.Write.ALWAYS)
    private LocalDateTime updatedAt;

    @Field(value = MongoKeyConstants.CHECKOUT.IS_CHECKOUT_COMPLETE_KEY, write = Field.Write.ALWAYS)
    private Boolean isCheckoutCompleted;

    @Field(value = MongoKeyConstants.CHECKOUT.CUSTOMER_EMAIL_KEY, write = Field.Write.ALWAYS)
    private String customerEmail;

    @Field(value = MongoKeyConstants.CHECKOUT.CUSTOMER_FIRST_NAME_KEY, write = Field.Write.ALWAYS)
    private String customerFirstName;

    @Field(value = MongoKeyConstants.CHECKOUT.CUSTOMER_LAST_NAME_KEY, write = Field.Write.ALWAYS)
    private String customerLastName;

    @Field(value = MongoKeyConstants.CHECKOUT.CUSTOMER_PHONE_NUMBER_KEY, write = Field.Write.ALWAYS)
    private String customerPhoneNo;

    @Field(value = MongoKeyConstants.CHECKOUT.CHECKOUT_URL_KEY, write = Field.Write.ALWAYS)
    private String checkoutUrl;

    @Field(value = MongoKeyConstants.CHECKOUT.ABANDON_NOTIFICATION_DETAILS_KEY, write = Field.Write.ALWAYS)
    private List<NotificationDetail> abandonNotificationDetails;

    @Field(value = MongoKeyConstants.CHECKOUT.NEXT_NOTIFICATION_TIME_KEY, write = Field.Write.ALWAYS)
    private LocalDateTime nextNotificationTime;

    @Field(value = MongoKeyConstants.CHECKOUT.NEXT_NOTIFICATION_INDEX_KEY, write = Field.Write.ALWAYS)
    private Integer nextNotificationIndex;

    @Data
    @Builder
    public static class NotificationDetail {

        @Field(value = MongoKeyConstants.CHECKOUT.NOTIFICATION_TIME_KEY, write = Field.Write.ALWAYS)
        private LocalDateTime notificationTime;

        @Field(value = MongoKeyConstants.CHECKOUT.NOTIFICATION_MEDIUM_KEY, write = Field.Write.ALWAYS)
        private List<String> notificationMedium;
    }

}
