package com.twilio.blogtfa.domain.services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;
import com.twilio.blogtfa.domain.models.User;
import com.twilio.blogtfa.domain.repositories.UserRepository;

@Singleton
public class ConfigurePhoneNumber {

  private UserRepository userRepository;

  @Inject
  public ConfigurePhoneNumber(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Transactional
  public User exec(User user, String phoneNumber) {
    user.setPhoneNumber(phoneNumber);
    return userRepository.save(user);
  }

}
