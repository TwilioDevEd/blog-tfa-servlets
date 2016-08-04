package com.twilio.blogtfa.infrastructure.guice;

import com.google.inject.servlet.ServletModule;
import com.twilio.blogtfa.application.servlets.EnableTfaViaAppServlet;
import com.twilio.blogtfa.application.servlets.EnableTfaViaSmsServlet;
import com.twilio.blogtfa.application.servlets.IndexServlet;
import com.twilio.blogtfa.application.servlets.IntegrationTestServlet;
import com.twilio.blogtfa.application.servlets.LogoutServlet;
import com.twilio.blogtfa.application.servlets.SignUpServlet;
import com.twilio.blogtfa.application.servlets.UserServlet;
import com.twilio.blogtfa.application.servlets.VerifyTFAServlet;

class BlogTFAServletModule extends ServletModule {

  @Override
  public void configureServlets() {
//    filter("/*").through(PersistFilter.class);

    serve("/").with(IndexServlet.class);
    serve("/user/").with(UserServlet.class);
    serve("/sign-up/").with(SignUpServlet.class);
    serve("/logout/").with(LogoutServlet.class);
    serve("/verify-tfa/").with(VerifyTFAServlet.class);
    serve("/enable-tfa-via-app/").with(EnableTfaViaAppServlet.class);
    serve("/enable-tfa-via-sms/").with(EnableTfaViaSmsServlet.class);
    if ("test".equals(System.getProperty("tfa"))) {
      serve("/test/set-up/").with(IntegrationTestServlet.class);
    }
  }

}
