package com.twilio.blogtfa.infrastructure.repositories;

import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.twilio.blogtfa.domain.models.User;
import com.twilio.blogtfa.domain.repositories.UserRepository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.Optional;

@Singleton
public class UserJpaRepository implements UserRepository {

  private Provider<EntityManager> entityManagerProvider;

  @Inject
  public UserJpaRepository(final Provider<EntityManager> provider) {
    this.entityManagerProvider = provider;
  }

  @Override
  public User save(final User user) {
    EntityManager entityManager = entityManagerProvider.get();
    User merge = entityManager.merge(user);
    entityManager.flush();
    return merge;
  }

  @Override
  public Optional<User> findByUsername(String username) {
    EntityManager entityManager = entityManagerProvider.get();
    return entityManager
      .createQuery("SELECT u FROM User u WHERE u.username = :username")
      .setParameter("username", username.toLowerCase())
      .getResultList()
      .stream()
      .findFirst();
  }

  @Override
  public void deleteAll() {
    EntityManager entityManager = entityManagerProvider.get();
    entityManager
      .createQuery("SELECT u FROM User u")
      .getResultList()
      .stream()
      .forEach(u -> entityManager.remove(u));
  }
}
