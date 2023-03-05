package com.akshay.connect.Models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunicationMessage {
    private String body;
    private String medium;
    private String email;
    private String mobileNo;
    private Integer maxRetryCount;
    private LocalDateTime maxRetryTime;
}
