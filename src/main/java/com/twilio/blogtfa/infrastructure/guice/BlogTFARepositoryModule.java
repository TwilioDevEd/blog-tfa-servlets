package com.twilio.blogtfa.infrastructure.guice;

import com.google.inject.AbstractModule;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.twilio.blogtfa.domain.repositories.UserRepository;
import com.twilio.blogtfa.infrastructure.repositories.UserJpaRepository;

import static com.twilio.blogtfa.infrastructure.guice.BlogTFAProperties.ENVIRONMENT;

public class BlogTFARepositoryModule extends AbstractModule {

  @Override
  protected void configure() {
    if ("test".equals(BlogTFAProperties.getDotenv().get(ENVIRONMENT))) {
      install(new JpaPersistModule("jpa-blogtfa-test"));
    } else {
      install(new JpaPersistModule("jpa-blogtfa"));
    }

    bind(UserRepository.class).to(UserJpaRepository.class);
  }

}
