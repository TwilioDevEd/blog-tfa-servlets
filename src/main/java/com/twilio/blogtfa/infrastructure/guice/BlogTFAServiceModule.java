package com.twilio.blogtfa.infrastructure.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import com.twilio.blogtfa.domain.services.LogIn;
import com.twilio.blogtfa.domain.services.SendSms;
import com.twilio.blogtfa.domain.services.SignUp;
import com.twilio.blogtfa.domain.services.ValidateToken;
import com.twilio.blogtfa.infrastructure.services.TwilioSMSSender;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Account;
import ru.vyarus.guice.validator.ImplicitValidationModule;

import static com.twilio.blogtfa.infrastructure.guice.BlogTFAProperties.TWILIO_ACCOUNT_SID;
import static com.twilio.blogtfa.infrastructure.guice.BlogTFAProperties.TWILIO_AUTH_TOKEN;

public class BlogTFAServiceModule extends AbstractModule {
  @Override
  protected void configure() {
    bind(LogIn.class);
    bind(SendSms.class).to(TwilioSMSSender.class);
    bind(SignUp.class);
    bind(ValidateToken.class);

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
