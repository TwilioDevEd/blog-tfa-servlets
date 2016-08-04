package com.twilio.blogtfa.infrastructure.repositories;

import com.google.inject.Singleton;
import com.twilio.blogtfa.domain.models.User;
import com.twilio.blogtfa.domain.repositories.UserRepository;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Singleton
public class UserInMemoryRepository implements UserRepository {

  private Map<String, User> users = new HashMap<>();
  private ScriptEngine engine;

  public UserInMemoryRepository() {
    try {
      ScriptEngineManager sem = new ScriptEngineManager();
      this.engine = sem.getEngineByName("javascript");
      URL resource = getClass().getResource("/users-data.json");
      Path path = Paths.get(resource.toURI());
      String json = new String(Files.readAllBytes(path));
      String script = "Java.asJSONCompatible(" + json + ")";
      Object result = this.engine.eval(script);
      List list = (List) result;
      list.forEach(o -> {
        Map userMap = (Map) o;
        User user = new User.Builder()
          .username((String) userMap.get("username"))
          .passwordHash((String) userMap.get("passwordHash"))
          .phoneNumber((String) userMap.get("phoneNumber"))
          .totpSecret((String) userMap.get("totpSecret"))
          .totpEnabledViaApp((Boolean) userMap.get("totpEnabledViaApp"))
          .totpEnabledViaSms((Boolean) userMap.get("totpEnabledViaSms"))
          .build();

        users.put(user.getId(), user);
      });

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
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
