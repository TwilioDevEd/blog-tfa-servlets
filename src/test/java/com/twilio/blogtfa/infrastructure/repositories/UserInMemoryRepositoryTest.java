package com.twilio.blogtfa.infrastructure.repositories;

import com.twilio.blogtfa.domain.models.User;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertTrue;

public class UserInMemoryRepositoryTest {

  private UserInMemoryRepository userInMemoryRepository;

  @Before
  public void setUp() {
    this.userInMemoryRepository = new UserInMemoryRepository();
  }

  @Test
  public void shouldSaveAndFindByUsername() throws Exception {
    this.userInMemoryRepository.save(new User("user10", "pass10"));
    Optional<User> user10 = this.userInMemoryRepository.findByUsername("user10");
    assertTrue(user10.isPresent());
  }

  @Test
  public void shouldSaveAndFindByUsernameCaseInsensitive() throws Exception {
    this.userInMemoryRepository.save(new User("user10", "pass10"));
    Optional<User> user10 = this.userInMemoryRepository.findByUsername("uSeR10");
    assertTrue(user10.isPresent());
  }

}
