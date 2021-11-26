package com.scheduler.Messaging;

import com.scheduler.Configurations.SMSConfiguration;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SMSResponse implements SMSSender{

    private static final Logger LOGGER = LoggerFactory.getLogger(SMSResponse.class);
    private final SMSConfiguration smsConfiguration;

    @Autowired
    public SMSResponse(SMSConfiguration smsConfiguration) {
        this.smsConfiguration = smsConfiguration;
    }

    @Override
    public void sendSMS(SMSRequest smsRequest) {

        if (isPhoneNumberValid(smsRequest.getPhoneNumber())){

            PhoneNumber to = new PhoneNumber(smsRequest.getPhoneNumber());
            PhoneNumber from = new PhoneNumber(smsConfiguration.getTwilioSenderNumber());
            String message = smsRequest.getMessage();

            MessageCreator creator = Message.creator(to, from, message);
            creator.create();
            LOGGER.info("Send sm {}" + smsRequest);
        }
        else {
            throw new IllegalArgumentException("Phone Number [" + smsRequest.getPhoneNumber() + "] is not valid");
        }

    }

    private boolean isPhoneNumberValid(String phoneNumber) {
        // TODO: Implement phone number validation
        return true;
    }
}
