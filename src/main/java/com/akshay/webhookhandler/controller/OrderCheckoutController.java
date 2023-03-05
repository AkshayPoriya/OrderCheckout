package com.akshay.webhookhandler.controller;

import com.akshay.common.dto.response.Response;
import com.akshay.webhookhandler.constants.ControllerUri;
import com.akshay.webhookhandler.service.EventReceiverService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ControllerUri.BASE_URL)
@Tag(name = "Checkout events webhook controller")
public class OrderCheckoutController {

    @Autowired
    private EventReceiverService eventReceiverService;


    @ResponseBody
    @PostMapping(ControllerUri.CHECKOUT_CONTROLLER.ORDER_END_POINT)
    @Operation(summary = "Listening webhook callbacks for create order events", method = "POST")
    public Response createOrder(@PathVariable("PARTNER") String partner, @RequestBody String incomingWebhook)  {
        return eventReceiverService.recordOrderEvent(partner, incomingWebhook);
    }

    @ResponseBody
    @RequestMapping(value = ControllerUri.CHECKOUT_CONTROLLER.ABANDON_CHECKOUT_END_POINT, method = RequestMethod.POST)
    @Operation(summary = "Listening webhook callbacks for abandon checkout events", method = "POST")
    public Response abandonCheckout(@PathVariable("PARTNER") String partner, @RequestBody String incomingWebhook)  {
        return eventReceiverService.recordAbandonCheckoutEvent(partner, incomingWebhook);
    }
}
