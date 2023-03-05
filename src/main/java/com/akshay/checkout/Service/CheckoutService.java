package com.akshay.checkout.Service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

public interface CheckoutService {
    @Async
    public void CreateCheckout(String partner, String data);

    public void SendAbandonNotification();
}
