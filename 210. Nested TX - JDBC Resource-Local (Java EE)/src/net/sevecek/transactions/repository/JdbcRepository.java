package net.sevecek.transactions.repository;

import java.sql.*;

public class JdbcRepository {

    public int queryDatabase(Connection conn) {
        try {
            try (PreparedStatement stmnt = conn.prepareStatement(
                    "select Version from CUSTOMERS where id = 1")) {
                try (ResultSet tableIterator = stmnt.executeQuery()) {
                    tableIterator.next();
                    int number = tableIterator.getInt("Version");
                    return number;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void updateDatabase(Connection conn, int newValue) {
        try {
            try (PreparedStatement stmnt = conn.prepareStatement(
                    "update CUSTOMERS set Version = ? where ID = 1")) {
                stmnt.setInt(1, newValue);
                int result = stmnt.executeUpdate();

                if (result != 1) {
                    throw new IllegalStateException("There whould be 1 changed record but there were " + result + " changes %n");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
