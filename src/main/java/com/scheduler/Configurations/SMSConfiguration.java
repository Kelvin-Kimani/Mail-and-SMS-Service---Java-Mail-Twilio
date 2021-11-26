package com.scheduler.Configurations;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@NoArgsConstructor
@Getter
@Setter
public class SMSConfiguration {

    @Value("${accountSID}")
    private String accountSID;

    @Value("${accountAuthToken}")
    private String accountAuthToken;

    @Value("${twilioSenderNumber}")
    private String twilioSenderNumber;

}
