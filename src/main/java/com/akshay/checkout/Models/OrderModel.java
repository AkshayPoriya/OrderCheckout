package com.akshay.checkout.Models;

import com.akshay.checkout.Constants.ApplicationConstants;
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

    @Field(value = "checkout_id", write = Field.Write.ALWAYS)
    private String checkoutId;

    @Field(value = "order_id", write = Field.Write.ALWAYS)
    private String orderId;

    @Field(value = "created_at", write = Field.Write.ALWAYS)
    private LocalDateTime createdAt;

    @Field(value = "order_status_url", write = Field.Write.ALWAYS)
    private String orderStatusUrl;

    @Field(value = "tracking_url", write = Field.Write.ALWAYS)
    private String trackingUrl;

    // Other details
}
