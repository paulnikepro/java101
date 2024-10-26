package org.paulnikepro.hw3.repository;

import org.paulnikepro.hw3.entity.User;

import java.sql.*;

public class UserRepositoryImpl implements UserRepository {
    private static final String URL = "jdbc:sqlite:users.db.sqlite";
    //private static final String URL = "jdbc:sqlite:C:\\Learning\\Java101\\java101\\Hw3\\users.db.sqlite";

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

            pstmt.setString(1, user.getEmail());
            pstmt.setString(2, user.getPhoneNumber());
            pstmt.setString(3, user.getPassword());

            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getLong(1));
                }
            }

        } catch (SQLException e) {
            // Consider logging instead of printing
            e.printStackTrace();
        }

        return user;
    }

    // Finds a user by ID from the database.
    @Override
    public User findById(Long id) {
        String query = "SELECT * FROM users WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setLong(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getLong("id"),
                            rs.getString("email"),
                            rs.getString("phoneNumber"),
                            rs.getString("password")
                    );
                }
            }

        } catch (SQLException e) {
            // Consider logging instead of printing
            e.printStackTrace();
        }

        return null;
    }
}



