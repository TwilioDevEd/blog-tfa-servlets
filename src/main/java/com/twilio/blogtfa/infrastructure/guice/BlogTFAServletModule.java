package com.twilio.blogtfa.infrastructure.guice;

import com.google.inject.servlet.ServletModule;
import com.twilio.blogtfa.application.servlets.MainPageServlet;

class BlogTFAServletModule extends ServletModule {

  @Override
  public void configureServlets() {
    serve("/").with(MainPageServlet.class);
  }

}
