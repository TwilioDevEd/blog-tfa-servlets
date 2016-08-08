package com.twilio.blogtfa.domain.services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.twilio.blogtfa.domain.exceptions.DomainException;
import com.twilio.blogtfa.domain.models.User;
import com.twilio.blogtfa.domain.repositories.UserRepository;

import java.util.Optional;

@Singleton
public class LogIn {

  private UserRepository userRepository;

  @Inject
  public LogIn(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User exec(String username, String password) {
    Optional<User> optUser = userRepository.findByUsername(username);
    if (!optUser.isPresent()) {
      throw new DomainException("Incorrect Username or Password");
    } else {
      User user = optUser.get();
      if (!user.authenticate(password)) {
        throw new DomainException("Incorrect Username or Password");
      } else {
        return user;
      }
    }
  }

}
