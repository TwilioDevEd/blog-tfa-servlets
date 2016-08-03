package com.twilio.blogtfa.domain.models;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UserTest {

  @Test
  public void shouldGenerateId() {
    User user = new User("user", "password");

    assertThat(user.getId(), is(notNullValue()));
    assertTrue(user.getId().matches("^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$"));
  }

  @Test
  public void shouldAuthenticate() {
    User user = new User("user", "password");

    assertTrue(user.authenticate("password"));
    assertFalse(user.authenticate("password2"));
  }
}
