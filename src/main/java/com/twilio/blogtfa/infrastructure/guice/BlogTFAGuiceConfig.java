package com.twilio.blogtfa.infrastructure.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

public class BlogTFAGuiceConfig extends GuiceServletContextListener {

  @Override
  protected Injector getInjector() {
    BlogTFAProperties blogTFAProperties = new BlogTFAProperties();
    return Guice.createInjector(
      new BlogTFAPropertiesModule(blogTFAProperties),
      new BlogTFAServletModule(blogTFAProperties),
      new BlogTFARepositoryModule(),
      new BlogTFAServiceModule(blogTFAProperties)
    );
  }

}
