package org.paulnikepro.hw4;

import org.hibernate.Session;
import java.util.Optional;

public class UserRepository {
    private final Session session;

    public UserRepository(Session session) {
        this.session = session;
    }

    public User create(User user) {
        session.persist(user);
        return user;
    }

    public User update(User user) {
        session.update(user);
        return user;
    }

    public Optional<User> findById(Long id) {
        User user = session.get(User.class, id);
        return Optional.ofNullable(user);
    }
}
