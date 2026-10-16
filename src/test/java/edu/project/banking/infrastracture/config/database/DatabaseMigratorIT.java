package edu.project.banking.infrastracture.config.database;

import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
class DatabaseMigratorIT {

    @Container
    static final PostgreSQLContainer POSTGRES = new PostgreSQLContainer("postgres:17")
            .withDatabaseName("banking_test")
            .withUsername("user")
            .withPassword("password");

    private static final List<String> EXPECTED_TABLES = List.of(
            "users",
            "accounts",
            "transfers",
            "ledger_entries",
            "databasechangelog",
            "databasechangeloglock");

    @Test
    void shouldApplyMigrationsAndBeIdempotent() throws Exception {
        String url = POSTGRES.getJdbcUrl();
        String username = POSTGRES.getUsername();
        String password = POSTGRES.getPassword();

        DatabaseMigrator.migrate(url, username, password);

        try(Connection connection = DriverManager.getConnection(url, username, password)) {
            for (String table : EXPECTED_TABLES) {
                assertTrue(tableExists(connection, table), "Expected table was not created " + table);
            }

            int appliedChangesets = countAppliedChangeSets(connection);

            assertTrue(appliedChangesets > 0, "Liquibase should have applied at least one changeset");

            DatabaseMigrator.migrate(url, username, password);

            int changeSetsAfterSecondRun = countAppliedChangeSets(connection);

            assertEquals(appliedChangesets, changeSetsAfterSecondRun, "Running migrations twice must not apply changesets again");
        }
    }

    private int countAppliedChangeSets(Connection connection) throws Exception {
        String sql = """
                SELECT COUNT(*) FROM public.databasechangelog;
                """;

        try(Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(sql);
            rs.next();
            return rs.getInt(1);
        }
    }

    private boolean tableExists(Connection connection, String tableName) throws Exception {
        String sql = """
                SELECT EXISTS 
                    (SELECT 1 FROM information_schema.tables 
                              WHERE tables.table_schema = 'public' AND tables.table_name = ?)
                """;

        try(var statement = connection.prepareStatement(sql)) {
            statement.setString(1, tableName);

            try(ResultSet rs = statement.executeQuery()) {
                rs.next();

                return rs.getBoolean(1);
            }
        }
    }
}