package com.twilio.blogtfa.domain.models;

import org.hibernate.validator.constraints.NotBlank;
import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Random;
import java.util.UUID;
import java.util.stream.IntStream;

@Entity
public class User {

  @Id
  private String id;

  @Column(name = "USER_NAME")
  @NotBlank(message = "Username may not be blank")
  private String username;

  @Column(name = "PASSWORD_HASH")
  @NotBlank(message = "Password may not be blank")
  private String passwordHash;

  @Column(name = "PHONE_NUMBER")
  private String phoneNumber;

  @Column(name = "TOTP_SECRET")
  private String totpSecret;

  @Column(name = "TOTP_ENABLED_VIA_APP")
  private boolean totpEnabledViaApp;

  @Column(name = "TOTP_ENABLED_VIA_SMS")
  private boolean totpEnabledViaSms;

  // required by ORM
  public User() {
  }

  public User(final String username, final String password) {
    this.id = UUID.randomUUID().toString();
    this.username = username;
    this.passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());
    this.totpEnabledViaApp = false;
    this.totpEnabledViaSms = false;
    this.totpSecret = generateRandomSecret();
  }

  // Generates random base 32 secret compatible to Google Authenticator
  private String generateRandomSecret() {
    return IntStream.range(0, 16)
      .mapToObj(i -> {
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ234567";
        final Random random = new Random();
        return String.valueOf(chars.charAt(random.nextInt(chars.length())));
      })
      .reduce((a, b) -> a + b).get();
  }

  public boolean authenticate(final String password) {
    return BCrypt.checkpw(password, this.passwordHash);
  }

  public String getUsername() {
    return username;
  }

  public String getId() {
    return id;
  }

  public String getTotpSecret() {
    return totpSecret;
  }

  public boolean isTotpEnabledViaSms() {
    return totpEnabledViaSms;
  }

  public boolean isTotpEnabledViaApp() {
    return totpEnabledViaApp;
  }

  public boolean isTFAEnabled() {
    return isTotpEnabledViaApp() || isTotpEnabledViaSms();
  }

  public void setTotpEnabledViaApp(boolean totpEnabledViaApp) {
    this.totpEnabledViaApp = totpEnabledViaApp;
  }

  public void setTotpEnabledViaSms(boolean totpEnabledViaSms) {
    this.totpEnabledViaSms = totpEnabledViaSms;
  }

  private User(String username, String passwordHash, String phoneNumber, String totpSecret,
               boolean totpEnabledViaApp, boolean totpEnabledViaSms) {
    this.id = UUID.randomUUID().toString();
    this.username = username;
    this.passwordHash = passwordHash;
    this.phoneNumber = phoneNumber;
    this.totpSecret = totpSecret;
    this.totpEnabledViaApp = totpEnabledViaApp;
    this.totpEnabledViaSms = totpEnabledViaSms;
  }

  public static class Builder {

    private String username;
    private String passwordHash;
    private String phoneNumber;
    private String totpSecret;
    private boolean totpEnabledViaApp;
    private boolean totpEnabledViaSms;

    public Builder username(String username) {
      this.username = username;
      return this;
    }

    public Builder passwordHash(String passwordHash) {
      this.passwordHash = passwordHash;
      return this;
    }

    public Builder phoneNumber(String phoneNumber) {
      this.phoneNumber = phoneNumber;
      return this;
    }

    public Builder totpSecret(String totpSecret) {
      this.totpSecret = totpSecret;
      return this;
    }

    public Builder totpEnabledViaApp(boolean totpEnabledViaApp) {
      this.totpEnabledViaApp = totpEnabledViaApp;
      return this;
    }

    public Builder totpEnabledViaSms(boolean totpEnabledViaSms) {
      this.totpEnabledViaSms = totpEnabledViaSms;
      return this;
    }

    public User build() {
      return new User(username, passwordHash, phoneNumber,
        totpSecret, totpEnabledViaApp, totpEnabledViaSms);
    }
  }
}
