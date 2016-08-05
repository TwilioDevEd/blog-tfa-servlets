package com.twilio.blogtfa.infrastructure.guice;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

class BlogTFAPropertiesModule extends AbstractModule {

  private BlogTFAProperties blogTFAProperties;

  public BlogTFAPropertiesModule(BlogTFAProperties blogTFAProperties) {
    this.blogTFAProperties = blogTFAProperties;
  }

  @Override
  protected void configure() {
        Names.bindProperties(binder(), blogTFAProperties);
    }
}
