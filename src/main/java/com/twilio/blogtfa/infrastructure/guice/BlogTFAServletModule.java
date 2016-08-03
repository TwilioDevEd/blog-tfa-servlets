package com.twilio.blogtfa.infrastructure.guice;

import com.google.inject.servlet.ServletModule;
import com.twilio.blogtfa.application.servlets.MainPageServlet;
import com.twilio.blogtfa.application.servlets.SignUpServlet;
import com.twilio.blogtfa.application.servlets.UserServlet;

class BlogTFAServletModule extends ServletModule {

  @Override
  public void configureServlets() {
    serve("/").with(MainPageServlet.class);
    serve("/user/").with(UserServlet.class);
    serve("/sign-up/").with(SignUpServlet.class);
  }

}
