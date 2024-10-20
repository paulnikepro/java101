package org.paulnikepro.hw4;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        LiquibaseSetup.applyMigrations();

        Configuration cfg = new Configuration().configure();

        // Try-with-resources
        try (SessionFactory sessionFactory = cfg.buildSessionFactory();
             Session session = sessionFactory.openSession()) {

            Transaction transaction = session.beginTransaction();

            RoleRepository roleRepository = new RoleRepository(session);
            UserRepository userRepository = new UserRepository(session);

            // Create roles
            Role adminRole = new Role(null, "Admin");
            Role userRole = new Role(null, "User");
            roleRepository.create(adminRole);
            roleRepository.create(userRole);

            // Create a user
            User user = new User(null, "John Doe", "john.doe@example.com", new HashSet<>());
            user.getRoles().addAll(List.of(adminRole, userRole));
            userRepository.create(user);

            transaction.commit();

            // Start a new transaction for updating user info
            Transaction updateTransaction = session.beginTransaction();

            Optional<User> retrievedUserOpt = userRepository.findById(user.getId());
            if (retrievedUserOpt.isPresent()) {
                User retrievedUser = retrievedUserOpt.get();
                System.out.println("Before Update: " + retrievedUser.getName() + ", Email: " + retrievedUser.getEmail());

                // Update user information
                retrievedUser.setName("Jane Doe");
                retrievedUser.setEmail("jane.doe@example.com");
                userRepository.update(retrievedUser);

                updateTransaction.commit();

                // Verify updates
                Optional<User> updatedUserOpt = userRepository.findById(retrievedUser.getId());
                updatedUserOpt.ifPresent(updatedUser ->
                        System.out.println("After Update: " + updatedUser.getName() + ", Email: " + updatedUser.getEmail())
                );
            } else {
                System.out.println("User not found!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
