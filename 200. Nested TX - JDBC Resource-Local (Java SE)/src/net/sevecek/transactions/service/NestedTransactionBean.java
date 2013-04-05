package net.sevecek.transactions.service;

import java.sql.*;
import java.util.*;
import net.sevecek.transactions.repository.*;

import static net.sevecek.transactions.util.Resources.*;

public class NestedTransactionBean {

    JdbcRepository repository;


    public NestedTransactionBean(JdbcRepository repository) {
        this.repository = repository;
    }


    public void performNestedOperationAndCommit(Connection conn, int x, Collection<String> results) {
        try {
            Savepoint savepoint = conn.setSavepoint();  // Means conn.beginNestedTransaction()
            results.add(BEGIN_NESTED_TRANSACTION);

            performOperation(conn, x, results);

            conn.releaseSavepoint(savepoint);           // Means conn.commitNestedTransaction()
            results.add(COMMIT_NESTED_TRANSACTION);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void performNestedOperationAndRollback(Connection conn, int x, Collection<String> results) {
        try {
            Savepoint savepoint = conn.setSavepoint();  // Means conn.beginNestedTransaction()
            results.add(BEGIN_NESTED_TRANSACTION);

            performOperation(conn, x, results);

            conn.rollback(savepoint);                   // Means conn.rollbackNestedTransaction()
            results.add(ROLLBACK_NESTED_TRANSACTION);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private void performOperation(Connection conn, int x, Collection<String> results) {
        int nestedTransactionBeforeNumber = repository.queryDatabase(conn);
        results.add(NESTED_OR_PARALLEL_TRANSACTION_CAN_READ_VALUE + nestedTransactionBeforeNumber);

        repository.updateDatabase(conn, x);
        results.add(NESTED_OR_PARALLEL_TRANSACTION_UPDATES_VALUE + x);

        int nestedTransactionAfterNumber = repository.queryDatabase(conn);
        results.add(NESTED_OR_PARALLEL_TRANSACTION_CAN_READ_VALUE + nestedTransactionAfterNumber);
    }

}
