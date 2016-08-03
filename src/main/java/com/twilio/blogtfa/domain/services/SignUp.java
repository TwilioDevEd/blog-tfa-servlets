package com.twilio.blogtfa.domain.services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;
import com.twilio.blogtfa.domain.exceptions.DomainException;
import com.twilio.blogtfa.domain.models.User;
import com.twilio.blogtfa.domain.repositories.UserRepository;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Optional;

@Singleton
public class SignUp {

  private UserRepository userRepository;

  @Inject
  public SignUp(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Transactional
  public User exec(@NotBlank(message = "Username cannot be empty") String username,
                   @NotBlank(message = "Password1 cannot be empty") String password1,
                   @NotBlank(message = "Password2 cannot be empty") String password2) {
    Optional<User> optUser = userRepository.findByUsername(username);

    if (password1 != null && password2 != null && !password1.equals(password2)) {
      throw new DomainException("Passwords do not match.");
    } else if (optUser.isPresent()) {
      throw new DomainException("That username is already in use");
    } else {
      return userRepository.save(new User(username, password1));
    }
  }
}
