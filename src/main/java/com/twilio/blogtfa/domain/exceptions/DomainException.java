package com.twilio.blogtfa.domain.exceptions;

public class DomainException extends RuntimeException {

  public DomainException(String message) {
    super(message);
  }

  public DomainException(String message, Exception e) {
    super(message, e);
  }

}
