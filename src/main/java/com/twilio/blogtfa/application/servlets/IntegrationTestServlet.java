package com.twilio.blogtfa.application.servlets;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.twilio.blogtfa.domain.models.User;
import com.twilio.blogtfa.domain.repositories.UserRepository;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@Singleton
public class IntegrationTestServlet extends HttpServlet {

  private UserRepository userRepository;
  private ScriptEngine engine;

  @Inject
  public IntegrationTestServlet(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
    try {
      userRepository.deleteAll();

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
        userRepository.save(user);
      });
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
