package com.akshay.checkout.repository;

import com.akshay.checkout.Models.CheckoutModel;

public interface CheckoutRepository {

    public CheckoutModel getCheckoutModel(String checkoutId);

    public void setCheckoutComplete(String checkoutId);

    public void saveCheckoutObj(CheckoutModel checkoutModel);
}
