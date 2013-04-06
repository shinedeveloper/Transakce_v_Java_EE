package net.sevecek.transactions;

import java.sql.*;
import java.util.*;
import javax.sql.*;
import com.mysql.jdbc.jdbc2.optional.*;
import net.sevecek.transactions.repository.*;
import net.sevecek.transactions.service.*;

import static net.sevecek.transactions.util.Resources.*;

public class Main {

    private static DataSource dataSource;
    private static JdbcRepository repository;
    private static TopLevelTransactionBean topLevelTransactionBean;
    private static NestedTransactionBean nestedTransactionBean;


    public static void main(String[] args) throws SQLException {
        createComponents();

        Collection<String> results = new ArrayList<>();
        test1(results);
        results.add("--------------------");
        test2(results);
        printResults(results);
    }


    private static void test1(Collection<String> results) throws SQLException {
        int initialNumber = ((int) (Math.random() * 1000.0)) * 10;
        try (Connection firstConn = dataSource.getConnection()) {
            repository.updateDatabase(firstConn, initialNumber);
            results.add(NUMBER_IN_DB_BEFORE_ANY_TRANSACTION + initialNumber);
        }

        try (Connection secondConn = dataSource.getConnection()) {
            topLevelTransactionBean.performTopLevelOperationAndRollback(secondConn, initialNumber, results);
        }

        try (Connection thirdConn = dataSource.getConnection()) {
            int finalNumber = repository.queryDatabase(thirdConn);
            results.add(NUMBER_IN_DB_AFTER_ALL_TRANSACTIONS + finalNumber);
        }
    }


    private static void test2(Collection<String> results) throws SQLException {
        int initialNumber = ((int) (Math.random() * 1000.0)) * 10;
        try (Connection firstConn = dataSource.getConnection()) {
            repository.updateDatabase(firstConn, initialNumber);
            results.add(NUMBER_IN_DB_BEFORE_ANY_TRANSACTION + initialNumber);
        }

        try (Connection secondConn = dataSource.getConnection()) {
            topLevelTransactionBean.performTopLevelOperationAndCommit(secondConn, initialNumber, results);
        }

        try (Connection thirdConn = dataSource.getConnection()) {
            int finalNumber = repository.queryDatabase(thirdConn);
            results.add(NUMBER_IN_DB_AFTER_ALL_TRANSACTIONS + finalNumber);
        }
    }

    private static void printResults(Collection<String> results) {
        for (String singleResult : results) {
            System.out.printf("%s %n", singleResult);
        }
    }


    private static void createComponents() {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUser("student");
        dataSource.setPassword("password");
        dataSource.setUrl("jdbc:mysql://localhost/VideoBoss");
        Main.dataSource = dataSource;

        Main.repository = new JdbcRepository();
        Main.nestedTransactionBean = new NestedTransactionBean(repository);
        Main.topLevelTransactionBean = new TopLevelTransactionBean(nestedTransactionBean, repository);
    }

}
