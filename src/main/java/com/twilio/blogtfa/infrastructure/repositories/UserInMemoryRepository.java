package com.twilio.blogtfa.infrastructure.repositories;

import com.google.inject.Singleton;
import com.twilio.blogtfa.domain.models.User;
import com.twilio.blogtfa.domain.repositories.UserRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Singleton
public class UserInMemoryRepository implements UserRepository {

  private Map<String, User> users = new HashMap<>();

  {
    User user = new User("user", "password");
    User user2 = new User("user2", "password");

    users.put(user.getId(), user);
    users.put(user2.getId(), user2);
  }

  @Override
  public User save(User user) {
    users.put(user.getId(), user);
    return user;
  }

  @Override
  public Optional<User> findByUsername(String username) {
    return users.values().stream()
      .filter(user -> user.getUsername().equalsIgnoreCase(username))
      .findFirst();
  }
}
