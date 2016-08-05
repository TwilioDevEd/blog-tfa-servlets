package com.twilio.blogtfa.domain.services;

import com.twilio.blogtfa.domain.models.User;

public interface SendSms {

  boolean exec(User user);

}
