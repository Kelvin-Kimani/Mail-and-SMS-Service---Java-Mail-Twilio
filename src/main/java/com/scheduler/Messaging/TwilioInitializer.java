package com.scheduler.Messaging;

import com.scheduler.Configurations.SMSConfiguration;
import com.twilio.Twilio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TwilioInitializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(TwilioInitializer.class);

    private SMSConfiguration smsConfiguration;

    @Autowired
    public TwilioInitializer(SMSConfiguration smsConfiguration) {
        this.smsConfiguration = smsConfiguration;
        Twilio.init(
                smsConfiguration.getAccountSID(),
                smsConfiguration.getAccountAuthToken()
        );

        LOGGER.info("Twilio Initialized with account sid {} ", smsConfiguration.getAccountSID());
    }
}
