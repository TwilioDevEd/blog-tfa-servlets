package com.twilio.blogtfa.domain.repositories;

import com.twilio.blogtfa.domain.models.User;

import java.util.Optional;

public interface UserRepository {

  User save(User user);

  Optional<User> findByUsername(String username);

}
