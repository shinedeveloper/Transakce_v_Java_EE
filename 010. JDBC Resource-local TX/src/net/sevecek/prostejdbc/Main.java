package net.sevecek.prostejdbc;

import java.sql.*;
import java.util.logging.*;
import javax.sql.*;
import com.mysql.jdbc.jdbc2.optional.*;
import net.sevecek.prostejdbc.entity.*;

public class Main {

    private static final Logger logger = Logger.getLogger(Main.class.getName());
    private static DataSource dataSource;


    public static void main(String[] args) throws SQLException {
        dataSource = getDataSource();

        Customer customer1 = new Customer(10L, "Kamil", "Ševeček", "Brno");
        Customer customer2 = new Customer(15L, "Jan", "Dvořák", "Plzeň");
        performCompoundDatabaseOperation(customer1, customer2);
    }


    private static void performCompoundDatabaseOperation(Customer cust1, Customer cust2) throws SQLException {
        Connection connection = dataSource.getConnection();
        try {
            connection.setAutoCommit(false);
            updateCustomer(connection, cust1);
            updateCustomer(connection, cust2);
            connection.commit();
        } finally {
            connection.close();
        }
    }


    private static void updateCustomer(Connection conn, Customer cust1) {
        try {
            PreparedStatement sqlStatement = conn
                    .prepareStatement("update Customers "
                            + "set FirstName = ?, LastName = ?, Address = ?, Deleted = ?, Version = Version+1 "
                            + "where ID=? and Version=?");
            try {
                sqlStatement.setString(1, cust1.getFirstName());
                sqlStatement.setString(2, cust1.getLastName());
                sqlStatement.setString(3, cust1.getAddress());
                sqlStatement.setBoolean(4, cust1.isDeleted());
                sqlStatement.setLong(5, cust1.getId());
                sqlStatement.setInt(6, cust1.getVersion());
                sqlStatement.execute();
            } finally {
                try {
                    sqlStatement.close();
                } catch (Exception ex) {
                    logger.warning(ex.getMessage());
                }
            }
        } catch (SQLException sqlex) {
            throw new RuntimeException("Spojení s databází selhalo", sqlex);
        }
    }


    private static DataSource getDataSource() {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUser("student");
        dataSource.setPassword("password");
        dataSource.setUrl("jdbc:mysql://localhost/VideoBoss");
        return dataSource;
    }

}
