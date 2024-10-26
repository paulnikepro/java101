package org.paulnikepro.hw4.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.paulnikepro.hw4.entity.User;

import java.util.Optional;

public class UserRepository extends BaseRepository<User> {

    public UserRepository(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Optional<User> findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            User user = session.get(User.class, id);
            return Optional.ofNullable(user);
        }
    }
}
