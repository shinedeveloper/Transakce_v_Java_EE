package net.sevecek.transactions.repository;

import java.sql.*;
import javax.annotation.*;
import javax.ejb.*;
import javax.sql.*;

@Singleton(name = "repository")
public class JdbcRepository {

    @Resource(lookup = "jdbc/VideoBoss")
    DataSource dataSource;


    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public int queryDatabase() {
        try {
            try (Connection conn = dataSource.getConnection()) {
                try (PreparedStatement stmnt = conn.prepareStatement(
                        "select Version from CUSTOMERS where id = 1")) {
                    try (ResultSet tableIterator = stmnt.executeQuery()) {
                        tableIterator.next();
                        int number = tableIterator.getInt("Version");
                        return number;
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateDatabase(int newValue) {
        try {
            try (Connection conn = dataSource.getConnection()) {
                try (PreparedStatement stmnt = conn.prepareStatement(
                        "update CUSTOMERS set Version = ? where ID = 1")) {
                    stmnt.setInt(1, newValue);
                    int result = stmnt.executeUpdate();

                    if (result != 1) {
                        throw new IllegalStateException("There whould be 1 changed record but there were " + result + " changes %n");
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
