package com.twilio.blogtfa.infrastructure.guice;

import com.google.inject.AbstractModule;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.twilio.blogtfa.domain.repositories.UserRepository;
import com.twilio.blogtfa.infrastructure.repositories.UserJpaRepository;

import static com.twilio.blogtfa.infrastructure.guice.BlogTFAProperties.ENVIRONMENT;

public class BlogTFARepositoryModule extends AbstractModule {
  @Override
  protected void configure() {
    if ("test".equals(BlogTFAProperties.getProp(ENVIRONMENT))) {
      install(new JpaPersistModule("jpa-blogtfa"));
    } else {
      install(new JpaPersistModule("jpa-blogtfa-test"));
    }

    bind(UserRepository.class).to(UserJpaRepository.class);
  }
}
