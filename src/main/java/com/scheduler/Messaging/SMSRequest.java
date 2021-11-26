package com.scheduler.Messaging;

import lombok.*;
import org.springframework.stereotype.Service;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Service
public class SMSRequest {
    private String phoneNumber;
    private String message;
}
