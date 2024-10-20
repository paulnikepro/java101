package org.paulnikepro.hw4;

import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.sql.Connection;
import java.sql.DriverManager;

public final class LiquibaseSetup {
    private LiquibaseSetup() {
        // private constructor to prevent instantiation
    }

    public static void applyMigrations() {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:hw4.database.sqlite")) {
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            Liquibase liquibase = new Liquibase("db/changelog/db.changelog-master.yaml", new ClassLoaderResourceAccessor(), database);
            liquibase.update(new Contexts());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
