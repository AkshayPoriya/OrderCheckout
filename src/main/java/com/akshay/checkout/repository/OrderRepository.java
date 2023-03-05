package com.akshay.checkout.repository;

import com.akshay.checkout.Models.OrderModel;

public interface OrderRepository {
    public void saveOrderDetails(OrderModel orderModel);
}
