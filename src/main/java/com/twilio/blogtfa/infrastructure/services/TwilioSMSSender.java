package com.twilio.blogtfa.infrastructure.services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.twilio.blogtfa.domain.exceptions.DomainException;
import com.twilio.blogtfa.domain.models.User;
import com.twilio.blogtfa.domain.services.SendSms;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.jboss.aerogear.security.otp.Totp;

import java.util.List;

import static java.lang.String.format;
import static java.util.Arrays.asList;

@Singleton
public class TwilioSMSSender implements SendSms {

  private static final String QUEUED = "queued";
  private MessageFactory messageFactory;

  @Inject
  public TwilioSMSSender(MessageFactory messageFactory) {
    this.messageFactory = messageFactory;
  }

  @Override
  public boolean exec(User user) {
    try {
      final String token = new Totp(user.getTotpSecret()).now();
      final List<NameValuePair> params = asList(
        new BasicNameValuePair("To", user.getPhoneNumber()),
        new BasicNameValuePair("From", System.getenv("TWILIO_PHONE_NUMBER")),
        new BasicNameValuePair("Body", format("Use this code to log in: %s", token))
      );
      Message sms = messageFactory.create(params);
      return QUEUED.equals(sms.getStatus());
    } catch (TwilioRestException e) {
      throw new DomainException(e.getErrorMessage(), e);
    }
  }

}
