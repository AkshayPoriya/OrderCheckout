package com.akshay.checkout.Service;

import org.springframework.scheduling.annotation.Async;

public interface OrderService {

    @Async
    public void CreateOrder(String partner,String data);
}
