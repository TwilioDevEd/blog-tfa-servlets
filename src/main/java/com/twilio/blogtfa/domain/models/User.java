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

  // required by ORM
  public User() {
  }

  public User(final String username, final String password) {
    this.id = UUID.randomUUID().toString();
    this.username = username;
    this.passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());
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

  private User(String id, String username, String passwordHash) {
    this.id = id;
    this.username = username;
    this.passwordHash = passwordHash;
  }

  public static class Builder {

    private String username;
    private String passwordHash;

    public Builder username(String username) {
      this.username = username;
      return this;
    }

    public Builder passwordHash(String passwordHash) {
      this.passwordHash = passwordHash;
      return this;
    }

    public User build() {
      return new User(UUID.randomUUID().toString(), username, passwordHash);
    }
  }

}
