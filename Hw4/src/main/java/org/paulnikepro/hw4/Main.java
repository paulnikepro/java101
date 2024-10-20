package org.paulnikepro.hw4;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.HashSet;

@SuppressWarnings("checkstyle:HideUtilityClassConstructor")
public class Main {
    public static void main(String[] args) {
        LiquibaseSetup.applyMigrations();

        Configuration cfg = new Configuration().configure();
        try (SessionFactory sessionFactory = cfg.buildSessionFactory(); Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            // Create roles
            Role adminRole = new Role();
            adminRole.setName("Admin");
            Role userRole = new Role();
            userRole.setName("User");
            session.persist(adminRole);
            session.persist(userRole);

            // Create a user
            User user = new User();
            user.setName("John Doe");
            user.setEmail("john.doe@example.com");
            user.setRoles(new HashSet<>());
            user.getRoles().add(adminRole);
            user.getRoles().add(userRole);
            session.persist(user);

            transaction.commit();

            // Retrieve user by ID
            User retrievedUser = session.get(User.class, user.getId());
            System.out.println("Retrieved User: " + retrievedUser.getName() + ", Roles: " + retrievedUser.getRoles().size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
