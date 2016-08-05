package com.twilio.blogtfa.infrastructure.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import com.twilio.blogtfa.domain.services.ConfigurePhoneNumber;
import com.twilio.blogtfa.domain.services.EnableTfaViaApp;
import com.twilio.blogtfa.domain.services.EnableTfaViaSms;
import com.twilio.blogtfa.domain.services.LogIn;
import com.twilio.blogtfa.domain.services.SendSms;
import com.twilio.blogtfa.domain.services.SignUp;
import com.twilio.blogtfa.domain.services.VerifyToken;
import com.twilio.blogtfa.infrastructure.services.StubSMSSender;
import com.twilio.blogtfa.infrastructure.services.TwilioSMSSender;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Account;
import ru.vyarus.guice.validator.ImplicitValidationModule;

import static com.twilio.blogtfa.infrastructure.guice.BlogTFAProperties.ENVIRONMENT;
import static com.twilio.blogtfa.infrastructure.guice.BlogTFAProperties.TWILIO_ACCOUNT_SID;
import static com.twilio.blogtfa.infrastructure.guice.BlogTFAProperties.TWILIO_AUTH_TOKEN;

public class BlogTFAServiceModule extends AbstractModule {
  private BlogTFAProperties blogTFAProperties;

  public BlogTFAServiceModule(BlogTFAProperties blogTFAProperties) {
    this.blogTFAProperties = blogTFAProperties;
  }

  @Override
  protected void configure() {
    bind(LogIn.class);
    if ("test".equals(blogTFAProperties.getProperty(ENVIRONMENT))) {
      bind(SendSms.class).to(StubSMSSender.class);
    } else {
      bind(SendSms.class).to(TwilioSMSSender.class);
    }
    bind(SignUp.class);
    bind(VerifyToken.class);
    bind(EnableTfaViaApp.class);
    bind(EnableTfaViaSms.class);
    bind(ConfigurePhoneNumber.class);

    install(new ImplicitValidationModule());
  }

  @Provides
  public MessageFactory messageFactory(
    @Named(TWILIO_ACCOUNT_SID) final String twilioAccountSid,
    @Named(TWILIO_AUTH_TOKEN) final String twilioAuthToken) {
    final TwilioRestClient client = new TwilioRestClient(twilioAccountSid, twilioAuthToken);
    final Account account = client.getAccount();
    return account.getMessageFactory();
  }
}
