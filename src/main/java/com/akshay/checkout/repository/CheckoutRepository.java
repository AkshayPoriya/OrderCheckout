package com.akshay.checkout.repository;

import com.akshay.checkout.Models.CheckoutModel;

import java.util.List;

public interface CheckoutRepository {

    public CheckoutModel getCheckoutModel(String checkoutId);

    public void setCheckoutComplete(String checkoutId);

    public void saveCheckoutObj(CheckoutModel checkoutModel);

    public List<CheckoutModel> getAbandonCheckoutOrdersForNotifications();

    public void updateAbandonCheckoutNotificationDetails(CheckoutModel checkoutModel);
}
