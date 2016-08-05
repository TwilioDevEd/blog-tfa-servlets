package com.twilio.blogtfa.domain.services;

import com.google.inject.Singleton;
import com.twilio.blogtfa.application.servlets.AuthQrCodePngServlet;
import com.twilio.blogtfa.domain.exceptions.DomainException;
import com.twilio.blogtfa.domain.models.User;
import org.hibernate.validator.constraints.NotEmpty;
import org.jboss.aerogear.security.otp.Totp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class ValidateToken {

  static final Logger LOGGER = LoggerFactory.getLogger(AuthQrCodePngServlet.class);

  public void exec(User user, @NotEmpty(message = "Token cannot be empty") String token) {
    LOGGER.info("Verifying token {}", token);
    Totp totp = new Totp(user.getTotpSecret());
    if (!totp.verify(token)) {
      throw new DomainException("There was an error verifying your token. Please try again.");
    }
  }
}
