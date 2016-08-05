package com.twilio.blogtfa.infrastructure.guice;

import org.apache.commons.lang3.StringUtils;

import java.util.Properties;
import java.util.stream.Stream;

class BlogTFAProperties extends Properties {

    static final String TWILIO_ACCOUNT_SID = "TWILIO_ACCOUNT_SID";
    static final String TWILIO_AUTH_TOKEN = "TWILIO_AUTH_TOKEN";
    static final String TWILIO_PHONE_NUMBER = "TWILIO_PHONE_NUMBER";
    static final String ENVIRONMENT = "TFA_ENV";

    BlogTFAProperties() {
      this(getProp(TWILIO_ACCOUNT_SID), getProp(TWILIO_AUTH_TOKEN),
        getProp(TWILIO_PHONE_NUMBER), getProp(ENVIRONMENT));
    }

    BlogTFAProperties(final String accountSid, final String authToken,
                      final String phoneNumber, final String environment) {
      if (Stream.of(accountSid, authToken, phoneNumber, environment)
          .anyMatch(StringUtils::isEmpty)) {
          throw new IllegalArgumentException("All required environment variables must be set.");
      }

      put(TWILIO_ACCOUNT_SID, accountSid);
      put(TWILIO_AUTH_TOKEN, authToken);
      put(TWILIO_PHONE_NUMBER, phoneNumber);
      put(ENVIRONMENT, environment);

      System.out.println("TWILIO_ACCOUNT_SID="+accountSid);
      System.out.println("TWILIO_AUTH_TOKEN="+authToken);
      System.out.println("TWILIO_PHONE_NUMBER="+phoneNumber);
      System.out.println("ENVIRONMENT="+environment);
    }

    static String getProp(String key) {
      return System.getProperty(key) != null ? System.getProperty(key) : System.getenv(key);
    }
}
