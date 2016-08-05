package com.twilio.blogtfa.domain.services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;
import com.twilio.blogtfa.domain.models.User;
import com.twilio.blogtfa.domain.repositories.UserRepository;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

@Singleton
public class EnableTfaViaApp {

  private VerifyToken verifyToken;
  private UserRepository userRepository;

  @Inject
  public EnableTfaViaApp(VerifyToken verifyToken, UserRepository userRepository) {
    this.verifyToken = verifyToken;
    this.userRepository = userRepository;
  }

  @Transactional
  public User exec(@NotNull User user, @NotEmpty String token) {
    verifyToken.exec(user, token);
    user.setTotpEnabledViaApp(true);
    return userRepository.save(user);
  }

}
