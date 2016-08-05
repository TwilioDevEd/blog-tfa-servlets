package com.twilio.blogtfa.infrastructure.guice;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.stream.Stream;

class BlogTFAProperties extends Properties {

  static final Logger LOGGER = LoggerFactory.getLogger(BlogTFAProperties.class);
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
    if (Stream.of(accountSid, authToken, phoneNumber)
      .anyMatch(StringUtils::isEmpty)) {
      throw new IllegalArgumentException("All required environment variables must be set.");
    }

    put(TWILIO_ACCOUNT_SID, accountSid);
    put(TWILIO_AUTH_TOKEN, authToken);
    put(TWILIO_PHONE_NUMBER, phoneNumber);
    put(ENVIRONMENT, !StringUtils.isEmpty(environment) ? environment : "dev");

    LOGGER.info("TWILIO_ACCOUNT_SID={}", accountSid);
    LOGGER.info("TWILIO_AUTH_TOKEN={}", authToken);
    LOGGER.info("TWILIO_PHONE_NUMBER={}", phoneNumber);
    LOGGER.info("ENVIRONMENT={}", environment);
  }

  static String getProp(String key) {
    return System.getProperty(key) != null ? System.getProperty(key) : System.getenv(key);
  }
}
