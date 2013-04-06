package net.sevecek.transactions.repository;

import java.lang.reflect.*;
import java.sql.*;
import java.util.*;
import javax.naming.*;
import javax.sql.*;

public class JdbcRepository {

    DataSource dataSource;


    public JdbcRepository() {
        try {
            Context serverRegistry = new InitialContext();
            dataSource = (DataSource) serverRegistry.lookup("jdbc/VideoBoss");
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }


    public int queryDatabase(Collection<String> results) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement stmnt = conn.prepareStatement(
                    "select Version from CUSTOMERS where id = 1")) {
                try (ResultSet tableIterator = stmnt.executeQuery()) {
                    tableIterator.next();
                    int number = tableIterator.getInt("Version");
                    logConnection(conn, results);
                    return number;
                }
            }
        }
    }


    public void updateDatabase(int newValue, Collection<String> results) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement stmnt = conn.prepareStatement(
                    "update CUSTOMERS set Version = ? where ID = 1")) {
                stmnt.setInt(1, newValue);
                int result = stmnt.executeUpdate();

                logConnection(conn, results);
                if (result != 1) {
                    throw new IllegalStateException("There whould be 1 changed record but there were " + result + " changes %n");
                }
            }
        }
    }


    private void logConnection(Connection conn, Collection<String> results) throws SQLException {
        Object realConnection = null;
        try {
            Field conField = conn.getClass().getSuperclass().getSuperclass().getDeclaredField("con");
            conField.setAccessible(true);
            realConnection = conField.get(conn);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            realConnection = conn;
        }

        results.add(String.format(
                "[Thread %d] Using Connection %s with autoCommit=%b",
                Thread.currentThread().hashCode(),
                realConnection,
                conn.getAutoCommit()));
    }
}
