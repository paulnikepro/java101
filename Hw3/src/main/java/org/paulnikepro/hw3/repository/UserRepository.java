package org.paulnikepro.hw3.repository;

import org.paulnikepro.hw3.entity.User;

public interface UserRepository {
    User save(User user);

    User findById(Long id);
}
