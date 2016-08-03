package com.twilio.blogtfa.domain.services;

import com.twilio.blogtfa.domain.exceptions.DomainException;
import com.twilio.blogtfa.domain.models.User;
import com.twilio.blogtfa.infrastructure.repositories.UserInMemoryRepository;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.fail;

public class SignUpTest {

  private SignUp signUp;

  @Before
  public void setUp() {
    this.signUp = new SignUp(new UserInMemoryRepository());
  }

  @Test
  public void shouldThrowExceptionWhenPasswordDoNotMatch() {
    try {
      this.signUp.exec("user", "password1", "password2");
      fail();
    } catch (DomainException e) {
      assertThat(e.getMessage(), is("Passwords do not match."));
    }
  }

  @Test
  public void shouldThrowExceptionWhenUsernameIsAlreadyInUse() {
    try {
      this.signUp.exec("user", "password3", "password3");
      fail();
    } catch (DomainException e) {
      assertThat(e.getMessage(), is("That username is already in use"));
    }
  }

  @Test
  public void shouldSaveWhenDataIsCorrect() {
    User user = this.signUp.exec("newuser", "password", "password");
    assertThat(user.getId(), is(notNullValue()));
  }
}
