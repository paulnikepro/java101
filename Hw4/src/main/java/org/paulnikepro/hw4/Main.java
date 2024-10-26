package org.paulnikepro.hw4;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.paulnikepro.hw4.entity.Role;
import org.paulnikepro.hw4.entity.User;
import org.paulnikepro.hw4.repository.RoleRepository;
import org.paulnikepro.hw4.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        // Apply database migrations
        LiquibaseSetup.applyMigrations();

        // Configure Hibernate and create SessionFactory
        Configuration cfg = new Configuration().configure();
        try (SessionFactory sessionFactory = cfg.buildSessionFactory()) {

            // Initialize repositories with SessionFactory
            RoleRepository roleRepository = new RoleRepository(sessionFactory);
            UserRepository userRepository = new UserRepository(sessionFactory);

            // Create roles
            Role adminRole = new Role(null, "Admin");
            Role userRole = new Role(null, "User");
            roleRepository.create(adminRole);
            roleRepository.create(userRole);

            // Create a user and assign roles
            User user = new User(null, "John Doe", "john.doe@example.com", new HashSet<>());
            user.getRoles().addAll(List.of(adminRole, userRole));
            userRepository.create(user);

            // Retrieve and update user information
            Optional<User> retrievedUserOpt = userRepository.findById(user.getId());
            if (retrievedUserOpt.isPresent()) {
                User retrievedUser = retrievedUserOpt.get();
                System.out.println("Before Update: " + retrievedUser.getName() + ", Email: " + retrievedUser.getEmail());

                // Update user details
                retrievedUser.setName("Jane Doe");
                retrievedUser.setEmail("jane.doe@example.com");
                userRepository.update(retrievedUser);

                // Verify updates
                Optional<User> updatedUserOpt = userRepository.findById(retrievedUser.getId());
                updatedUserOpt.ifPresent(updatedUser ->
                        System.out.println("After Update: " + updatedUser.getName() + ", Email: " + updatedUser.getEmail())
                );
            } else {
                System.out.println("User not found!");
            }

        } catch (Exception e) {
            e.printStackTrace(); // better replace with  SLF4J Logger
        }
    }
}
