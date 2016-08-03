package com.twilio.blogtfa.infrastructure.guice;

import com.google.inject.AbstractModule;
import com.twilio.blogtfa.domain.services.SignUp;

public class BlogTFAServiceModule extends AbstractModule {
  @Override
  protected void configure() {
    bind(SignUp.class);
  }
}
