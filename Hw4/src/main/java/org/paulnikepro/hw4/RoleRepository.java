package org.paulnikepro.hw4;

import org.hibernate.Session;
import java.util.Optional;

public class RoleRepository {
    private final Session session;

    public RoleRepository(Session session) {
        this.session = session;
    }

    public Role create(Role role) {
        session.persist(role);
        return role;
    }

    public Optional<Role> findById(Long id) {
        Role role = session.get(Role.class, id);
        return Optional.ofNullable(role);
    }
}
