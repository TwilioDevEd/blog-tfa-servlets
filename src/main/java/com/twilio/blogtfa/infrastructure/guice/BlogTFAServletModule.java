package com.twilio.blogtfa.infrastructure.guice;

import com.google.inject.servlet.ServletModule;
import com.twilio.blogtfa.application.servlets.IndexServlet;
import com.twilio.blogtfa.application.servlets.LogoutServlet;
import com.twilio.blogtfa.application.servlets.SignUpServlet;
import com.twilio.blogtfa.application.servlets.UserServlet;

class BlogTFAServletModule extends ServletModule {

  @Override
  public void configureServlets() {
//    filter("/*").through(PersistFilter.class);

    serve("/").with(IndexServlet.class);
    serve("/user/").with(UserServlet.class);
    serve("/sign-up/").with(SignUpServlet.class);
    serve("/logout/").with(LogoutServlet.class);
  }

}
