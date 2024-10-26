package org.paulnikepro.hw4.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.paulnikepro.hw4.entity.Role;

import java.util.Optional;

public class RoleRepository extends BaseRepository<Role> {

    public RoleRepository(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Optional<Role> findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Role role = session.get(Role.class, id);
            return Optional.ofNullable(role);
        }
    }
}
