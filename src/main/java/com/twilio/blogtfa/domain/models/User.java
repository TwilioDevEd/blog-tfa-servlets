package com.twilio.blogtfa.domain.models;

import org.hibernate.validator.constraints.NotBlank;
import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

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

  // required by ORM
  public User() { }

  public User(final String username, final String password) {
    this.id = UUID.randomUUID().toString();
    this.username = username;
    this.passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());
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
}
