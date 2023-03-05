package com.akshay.checkout.Service;

import org.springframework.scheduling.annotation.Async;

public interface CheckoutService {
    @Async
    public void CreateCheckout(String partner, String data);
}
