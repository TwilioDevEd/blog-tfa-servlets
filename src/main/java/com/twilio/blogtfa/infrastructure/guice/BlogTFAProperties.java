package com.twilio.blogtfa.infrastructure.guice;

import org.apache.commons.lang3.StringUtils;

import java.util.Properties;
import java.util.stream.Stream;

class BlogTFAProperties extends Properties {

    static final String TWILIO_ACCOUNT_SID = "TWILIO_ACCOUNT_SID";
    static final String TWILIO_AUTH_TOKEN = "TWILIO_AUTH_TOKEN";
    static final String TWILIO_PHONE_NUMBER = "TWILIO_PHONE_NUMBER";

    BlogTFAProperties() {
        this(System.getenv(TWILIO_ACCOUNT_SID),
                System.getenv(TWILIO_AUTH_TOKEN),
                System.getenv(TWILIO_PHONE_NUMBER)
        );
    }

    BlogTFAProperties(
            final String accountSid,
            final String authToken,
            final String phoneNumber) {
        if (Stream.of(accountSid, authToken, phoneNumber).anyMatch(StringUtils::isEmpty)) {
            throw new IllegalArgumentException("All required environment variables must be set.");
        }

        put(TWILIO_ACCOUNT_SID, accountSid);
        put(TWILIO_AUTH_TOKEN, authToken);
        put(TWILIO_PHONE_NUMBER, phoneNumber);
    }

}
