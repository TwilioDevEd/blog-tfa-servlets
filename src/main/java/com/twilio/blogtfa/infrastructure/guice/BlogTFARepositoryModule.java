package com.twilio.blogtfa.infrastructure.guice;

import com.google.inject.AbstractModule;
import com.twilio.blogtfa.domain.repositories.UserRepository;
import com.twilio.blogtfa.infrastructure.repositories.UserInMemoryRepository;

public class BlogTFARepositoryModule extends AbstractModule {
  @Override
  protected void configure() {
//    install(new JpaPersistModule("jpa-sms2fa"));

    bind(UserRepository.class).to(UserInMemoryRepository.class);
  }
}
