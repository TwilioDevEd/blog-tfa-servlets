package com.twilio.blogtfa.infrastructure.guice;

import com.google.inject.persist.PersistFilter;
import com.google.inject.servlet.ServletModule;
import com.twilio.blogtfa.application.filters.AuthenticationFilter;
import com.twilio.blogtfa.application.servlets.IndexServlet;
import com.twilio.blogtfa.application.servlets.LogoutServlet;
import com.twilio.blogtfa.application.servlets.SignUpServlet;
import com.twilio.blogtfa.application.servlets.UserServlet;
import com.twilio.blogtfa.infrastructure.test.IntegrationTestServlet;

import static com.twilio.blogtfa.infrastructure.guice.BlogTFAProperties.ENVIRONMENT;

class BlogTFAServletModule extends ServletModule {

  private BlogTFAProperties blogTFAProperties;

  public BlogTFAServletModule(BlogTFAProperties blogTFAProperties) {
    this.blogTFAProperties = blogTFAProperties;
  }

  @Override
  public void configureServlets() {
    filter("/*").through(PersistFilter.class);

    filter("/user/").through(AuthenticationFilter.class);

    serve("/").with(IndexServlet.class);
    serve("/user/").with(UserServlet.class);
    serve("/sign-up/").with(SignUpServlet.class);
    serve("/logout/").with(LogoutServlet.class);

    if ("test".equals(blogTFAProperties.getProperty(ENVIRONMENT))) {
      serve("/test/set-up/").with(IntegrationTestServlet.class);
    }
  }

}
