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

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Document(collection = ApplicationConstants.ORDER_COLLECTION_NAME)
public class OrderModel {

    @Field(value = MongoKeyConstants.ORDER.CHECKOUT_ID_KEY, write = Field.Write.ALWAYS)
    private String checkoutId;

    @Field(value = MongoKeyConstants.ORDER.ORDER_ID_KEY, write = Field.Write.ALWAYS)
    private String orderId;

    @Field(value = MongoKeyConstants.ORDER.CREATED_AT_KEY, write = Field.Write.ALWAYS)
    private LocalDateTime createdAt;

    @Field(value = MongoKeyConstants.ORDER.ORDER_STATUS_URL_KEY, write = Field.Write.ALWAYS)
    private String orderStatusUrl;

    @Field(value = MongoKeyConstants.ORDER.TRACKING_URL_KEY, write = Field.Write.ALWAYS)
    private String trackingUrl;

    // Other details
}
