package com.twilio.blogtfa.infrastructure.guice;

import io.github.cdimascio.dotenv.Dotenv;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.stream.Stream;

class BlogTFAProperties extends Properties {

  private static Dotenv dotenv = Dotenv.load();

  static final Logger LOGGER = LoggerFactory.getLogger(BlogTFAProperties.class);
  static final String TWILIO_ACCOUNT_SID = "TWILIO_ACCOUNT_SID";
  static final String TWILIO_AUTH_TOKEN = "TWILIO_AUTH_TOKEN";
  static final String TWILIO_PHONE_NUMBER = "TWILIO_PHONE_NUMBER";
  static final String ENVIRONMENT = "TFA_ENV";

  BlogTFAProperties() {
    this(dotenv.get(TWILIO_ACCOUNT_SID), dotenv.get(TWILIO_AUTH_TOKEN),
      dotenv.get(TWILIO_PHONE_NUMBER), dotenv.get(ENVIRONMENT));
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

  public static Dotenv getDotenv() {
    return dotenv;
  }
}
