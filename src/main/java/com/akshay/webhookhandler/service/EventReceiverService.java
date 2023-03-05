package com.akshay.webhookhandler.service;

import com.akshay.common.dto.response.Response;

public interface EventReceiverService {
    public Response recordOrderEvent(String partner, String msg);

    public Response recordAbandonCheckoutEvent(String partner, String msg);
}
