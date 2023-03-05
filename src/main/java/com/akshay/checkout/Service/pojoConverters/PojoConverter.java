package com.akshay.checkout.Service.pojoConverters;

import com.akshay.checkout.Models.CheckoutModel;
import com.akshay.checkout.Models.OrderModel;

public interface PojoConverter {
    public CheckoutModel getCheckoutPojo(String message);
    public OrderModel getOrderPojo(String message);
}
