package com.twilio.blogtfa.infrastructure.test;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;
import com.twilio.blogtfa.domain.models.User;
import com.twilio.blogtfa.domain.repositories.UserRepository;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@Singleton
public class IntegrationTestServlet extends HttpServlet {

  private final UserRepository userRepository;
  private ScriptEngine engine;

  @Inject
  public IntegrationTestServlet(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Transactional
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
    try {
      userRepository.deleteAll();
      readAndSaveUsers();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private void readAndSaveUsers() throws URISyntaxException, IOException, ScriptException {
    this.engine = new ScriptEngineManager().getEngineByName("javascript");
    URL resource = getClass().getResource("/users-data.json");
    Path path = Paths.get(resource.toURI());
    String json = new String(Files.readAllBytes(path));
    String script = "Java.asJSONCompatible(" + json + ")";
    Object result = this.engine.eval(script);
    List list = (List) result;
    list.forEach(o -> {
      saveUser((Map) o);
    });
  }

  private void saveUser(Map o) {
    Map userMap = o;
    User user = new User.Builder()
      .username((String) userMap.get("username"))
      .passwordHash((String) userMap.get("passwordHash"))
      .build();
    userRepository.save(user);
  }

}
