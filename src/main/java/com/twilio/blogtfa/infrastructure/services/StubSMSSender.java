package com.twilio.blogtfa.infrastructure.services;

import com.twilio.blogtfa.domain.models.User;
import com.twilio.blogtfa.domain.services.SendSms;

public class StubSMSSender implements SendSms {
  @Override
  public boolean exec(User user) {
    return true;
  }
}
