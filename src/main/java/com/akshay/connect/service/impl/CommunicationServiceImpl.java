package com.akshay.connect.service.impl;

import com.akshay.common.utils.CommonLogger;
import com.akshay.connect.Models.CommunicationMessage;
import com.akshay.connect.service.CommunicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommunicationServiceImpl implements CommunicationService {
    @Autowired
    private CommonLogger commonLogger;

    @Override
    public void sendCommunication(CommunicationMessage communicationMessage) {
        if("SMS".equalsIgnoreCase(communicationMessage.getMedium())){
            //TODO: send SMS
            commonLogger.logInfo(communicationMessage.getBody());
            commonLogger.logInfo(String.format("SMS successfully sent to number %s", communicationMessage.getMobileNo()));
        } else if ("EMAIL".equalsIgnoreCase(communicationMessage.getMedium())) {
            //TODO: send EMAIL
            commonLogger.logInfo(communicationMessage.getBody());
            commonLogger.logInfo(String.format("EMAIL successfully sent to email-id %s", communicationMessage.getEmail()));
        } else if ("WHATSAPP".equalsIgnoreCase(communicationMessage.getMedium())) {
            //TODO: send EMAIL
            commonLogger.logInfo(communicationMessage.getBody());
            commonLogger.logInfo(String.format("WHATSAPP successfully done to number %s", communicationMessage.getMobileNo()));
        }else{
            commonLogger.logInfo(String.format("Unknown medium %s received.", communicationMessage.getMedium()));
        }
    }
}
