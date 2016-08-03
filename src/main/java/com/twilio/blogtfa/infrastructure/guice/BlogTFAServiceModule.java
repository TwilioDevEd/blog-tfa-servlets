package com.twilio.blogtfa.infrastructure.guice;

import com.google.inject.AbstractModule;
import com.twilio.blogtfa.domain.services.LogIn;
import com.twilio.blogtfa.domain.services.SignUp;
import ru.vyarus.guice.validator.ImplicitValidationModule;

public class BlogTFAServiceModule extends AbstractModule {
  @Override
  protected void configure() {
    bind(SignUp.class);
    bind(LogIn.class);

    install(new ImplicitValidationModule());
  }
}
