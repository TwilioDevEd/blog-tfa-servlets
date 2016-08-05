package com.twilio.blogtfa.domain.models;

import org.junit.Test;

import static java.lang.String.format;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UserTest {

  @Test
  public void shouldBuildUser() {
    User user = new User("user", "password");

    assertThat(user.getId(), is(notNullValue()));
    String regex = "^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$";
    assertTrue(user.getId().matches(regex));

    assertTrue(format("Secret %s should match pattern", user.getTotpSecret()),
      user.getTotpSecret().matches("^[A-Z2-7]{16}$"));

    assertFalse(user.isTotpEnabledViaApp());
    assertFalse(user.isTotpEnabledViaSms());
  }

  @Test
  public void shouldAuthenticate() {
    User user = new User("user", "password");

    assertTrue(user.authenticate("password"));
    assertFalse(user.authenticate("password2"));
  }
}
