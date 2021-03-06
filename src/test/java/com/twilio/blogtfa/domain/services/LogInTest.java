package com.twilio.blogtfa.domain.services;

import com.twilio.blogtfa.domain.exceptions.DomainException;
import com.twilio.blogtfa.domain.models.User;
import com.twilio.blogtfa.infrastructure.repositories.UserInMemoryRepository;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.fail;

public class LogInTest {

  private LogIn logIn;
  private UserInMemoryRepository userRepository;

  @Before
  public void setUp() {
    userRepository = new UserInMemoryRepository();
    userRepository.save(new User.Builder()
      .username("user")
      .passwordHash("$2a$12$J2dTN4wMH7nbVDgkYq/d8uTsiTw3DQHNF5Py98Mf27PJvnqkE94iK")
      .build());
    this.logIn = new LogIn(userRepository);
  }

  @Test
  public void shouldThrowExceptionWhenUserDoesNotExit() {
    try {
      this.logIn.exec("non-existent-user", "password");
      fail();
    } catch (DomainException e) {
      assertThat(e.getMessage(), is("Incorrect Username or Password"));
    }
  }

  @Test
  public void shouldThrowExceptionWhenPasswordIsWrong() {
    try {
      this.logIn.exec("user", "wrong-pass");
      fail();
    } catch (DomainException e) {
      assertThat(e.getMessage(), is("Incorrect Username or Password"));
    }
  }

  @Test
  public void shouldLogIn() {
    User user = this.logIn.exec("user", "password");
    assertThat(user, is(notNullValue()));
  }

}
