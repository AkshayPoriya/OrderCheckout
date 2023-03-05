package com.akshay.connect.service;

import com.akshay.connect.Models.CommunicationMessage;
import org.springframework.scheduling.annotation.Async;

import java.time.LocalDateTime;
import java.util.List;

public interface CommunicationService {

    @Async
    public void sendCommunication(CommunicationMessage communicationMessage);
}
