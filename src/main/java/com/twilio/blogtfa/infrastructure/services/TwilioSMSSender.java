package com.twilio.blogtfa.infrastructure.services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.twilio.blogtfa.domain.models.User;
import com.twilio.blogtfa.domain.services.SendSms;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.jboss.aerogear.security.otp.Totp;
import org.slf4j.Logger;

import java.util.List;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.slf4j.LoggerFactory.getLogger;

@Singleton
public class TwilioSMSSender implements SendSms {

  private static final Logger LOGGER = getLogger(TwilioSMSSender.class);
  private static final String QUEUED = "queued";

  private final String twilioPhoneNumber;
  private final MessageFactory messageFactory;

  @Inject
  public TwilioSMSSender(@Named("TWILIO_PHONE_NUMBER") String twilioPhoneNumber,
                         MessageFactory messageFactory) {
    this.twilioPhoneNumber = twilioPhoneNumber;
    this.messageFactory = messageFactory;
  }

  @Override
  public boolean exec(User user) {
    if (isEmpty(user.getPhoneNumber())) {
      return false;
    }

    try {
      final String token = new Totp(user.getTotpSecret()).now();
      LOGGER.info("Generated token is {}", token);
      final List<NameValuePair> params = asList(
        new BasicNameValuePair("To", user.getPhoneNumber()),
        new BasicNameValuePair("From", twilioPhoneNumber),
        new BasicNameValuePair("Body", format("Use this code to log in: %s", token))
      );
      Message sms = messageFactory.create(params);
      return QUEUED.equals(sms.getStatus());
    } catch (TwilioRestException e) {
      LOGGER.warn(format("Error on sending message to %s", user.getPhoneNumber()), e);
      return false;
    }
  }

}
