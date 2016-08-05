package com.twilio.blogtfa.domain.services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;
import com.twilio.blogtfa.domain.models.User;
import com.twilio.blogtfa.domain.repositories.UserRepository;

@Singleton
public class EnableTfaViaSms {

  private UserRepository userRepository;
  private ValidateToken validateToken;

  @Inject
  public EnableTfaViaSms(UserRepository userRepository, ValidateToken validateToken) {
    this.userRepository = userRepository;
    this.validateToken = validateToken;
  }

  @Transactional
  public User exec(User user, String token) {
    validateToken.exec(user, token);
    user.setTotpEnabledViaSms(true);
    return userRepository.save(user);
  }
}
