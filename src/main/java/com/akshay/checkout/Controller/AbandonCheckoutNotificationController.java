package com.akshay.checkout.Controller;

import com.akshay.checkout.Constants.ApplicationConstants;
import com.akshay.checkout.Service.CheckoutService;
import com.akshay.common.dto.response.Response;
import com.akshay.webhookhandler.constants.ControllerUri;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApplicationConstants.BASE_URI)
@Tag(name = "Send Abandon Notifications Controller")
public class AbandonCheckoutNotificationController {

    @Autowired
    private CheckoutService checkoutService;


    @ResponseBody
    @PostMapping(ApplicationConstants.ABANDON_NOTIFICATION_END_POINT)
    @Operation(summary = "Send notifications for abandoned checkouts", method = "POST")
    public Response sendNotifications()  {
        checkoutService.SendAbandonNotification();
        return Response.builder()
                .ok(Boolean.TRUE)
                .code("200")
                .result("Abandon Notification Task Completed Successfully!")
                .build();
    }

}
