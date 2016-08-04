package com.twilio.blogtfa.domain.services;

import com.google.inject.Singleton;
import com.twilio.blogtfa.domain.exceptions.DomainException;
import com.twilio.blogtfa.domain.models.User;
import org.hibernate.validator.constraints.NotEmpty;
import org.jboss.aerogear.security.otp.Totp;

@Singleton
public class ValidateToken {

  public void exec(User user, @NotEmpty(message = "Token cannot be empty") String token) {
    Totp totp = new Totp(user.getTotpSecret());
    if (!totp.verify(token)) {
      throw new DomainException("There was an error verifying your token. Please try again.");
    }
  }
}
