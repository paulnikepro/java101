package org.paulnikepro.hw3.repository;

import org.paulnikepro.hw3.entity.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {
    private static final String URL = "jdbc:sqlite:users.db.sqlite";
    private static final int EMAIL_INDEX = 1;
    private static final int PHONE_NUMBER_INDEX = 2;
    private static final int PASSWORD_INDEX = 3;

    // Constructor: Initializes the database table if it does not exist.
    public UserRepositoryImpl() {
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {

            String createTableQuery = """
                    CREATE TABLE IF NOT EXISTS users (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        email TEXT NOT NULL,
                        phoneNumber TEXT,
                        password TEXT NOT NULL
                    );
                    """;

            stmt.execute(createTableQuery);

        } catch (SQLException e) {
            // Consider logging instead of printing
            e.printStackTrace();
        }
    }

    // Saves a user to the database and returns the user with the generated ID.
    @Override
    public User save(User user) {
        String insertQuery = "INSERT INTO users (email, phoneNumber, password) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(EMAIL_INDEX, user.getEmail());
            pstmt.setString(PHONE_NUMBER_INDEX, user.getPhoneNumber());
            pstmt.setString(PASSWORD_INDEX, user.getPassword());

            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getLong(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    // Finds a user by ID from the database.
    @Override
    public Optional<User> findById(Long id) {
        String query = "SELECT * FROM users WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setLong(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new User(
                            rs.getLong("id"),
                            rs.getString("email"),
                            rs.getString("phoneNumber"),
                            rs.getString("password")
                    ));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }
}
