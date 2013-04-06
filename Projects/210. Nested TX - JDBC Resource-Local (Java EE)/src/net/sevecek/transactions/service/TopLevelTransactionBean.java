package net.sevecek.transactions.service;

import java.sql.*;
import java.util.*;
import net.sevecek.transactions.repository.*;

import static net.sevecek.transactions.util.Resources.*;

public class TopLevelTransactionBean {

    NestedTransactionBean nestedTransactionBean;
    JdbcRepository repository;


    public TopLevelTransactionBean(NestedTransactionBean nestedTransactionBean, JdbcRepository repository) {
        this.nestedTransactionBean = nestedTransactionBean;
        this.repository = repository;
    }


    public void performTopLevelOperationAndRollback(Connection conn, int value, Collection<String> results) {
        try {
            conn.setAutoCommit(false);                 // Means conn.beginTopLevelTransaction()
            results.add(BEGIN_TOP_LEVEL_TRANSACTION);

            int topLevelTransactionBeforeNumber = value+1;
            repository.updateDatabase(conn, topLevelTransactionBeforeNumber);
            results.add(TOP_LEVEL_TRANSACTION_JUST_UPDATED_VALUE + topLevelTransactionBeforeNumber);

            nestedTransactionBean.performNestedOperationAndCommit(conn, value + 5, results);

            int topLevelTransactionAfterNumber = repository.queryDatabase(conn);
            results.add(TOP_LEVEL_TRANSACTION_CAN_READ_VALUE + topLevelTransactionAfterNumber);

            conn.rollback();                           // Means conn.rollbackTopLevelTransaction()
            results.add(ROLLBACK_TOP_LEVEL_TRANSACTION);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void performTopLevelOperationAndCommit(Connection conn, int value, Collection<String> results) {
        try {
            conn.setAutoCommit(false);                 // Means conn.beginTopLevelTransaction()
            results.add(BEGIN_TOP_LEVEL_TRANSACTION);

            int topLevelTransactionBeforeNumber = value+1;
            repository.updateDatabase(conn, topLevelTransactionBeforeNumber);
            results.add(TOP_LEVEL_TRANSACTION_JUST_UPDATED_VALUE + topLevelTransactionBeforeNumber);

            nestedTransactionBean.performNestedOperationAndRollback(conn, value + 5, results);

            int topLevelTransactionAfterNumber = repository.queryDatabase(conn);
            results.add(TOP_LEVEL_TRANSACTION_CAN_READ_VALUE + topLevelTransactionAfterNumber);

            conn.commit();                             // Means conn.commitTopLevelTransaction()
            results.add(COMMIT_TOP_LEVEL_TRANSACTION);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
