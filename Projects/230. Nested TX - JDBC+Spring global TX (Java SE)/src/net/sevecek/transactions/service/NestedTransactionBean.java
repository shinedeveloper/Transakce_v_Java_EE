package net.sevecek.transactions.service;

import java.util.*;
import org.springframework.transaction.annotation.*;
import org.springframework.transaction.interceptor.*;
import net.sevecek.transactions.repository.*;

import static net.sevecek.transactions.util.Resources.*;

public class NestedTransactionBean {

    JdbcRepository repository;


    public NestedTransactionBean(JdbcRepository repository) {
        this.repository = repository;
    }


    @Deprecated
    protected NestedTransactionBean() {
        // This is here only for a Spring proxy. Do not call explicitly!
    }


    @Transactional(propagation = Propagation.NESTED)
    public void performNestedOperationAndCommit(int x, Collection<String> results) {
        results.add(BEGIN_NESTED_TRANSACTION);

        performOperation(x, results);

        results.add(COMMIT_NESTED_TRANSACTION);
    }


    @Transactional(propagation = Propagation.NESTED)
    public void performNestedOperationAndRollback(int x, Collection<String> results) {
        results.add(BEGIN_NESTED_TRANSACTION);

        performOperation(x, results);

        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        results.add(ROLLBACK_NESTED_TRANSACTION);
    }


    private void performOperation(int x, Collection<String> results) {
        int nestedTransactionBeforeNumber = repository.queryDatabase();
        results.add(NESTED_OR_PARALLEL_TRANSACTION_CAN_READ_VALUE + nestedTransactionBeforeNumber);

        results.add(NESTED_OR_PARALLEL_TRANSACTION__IS_TRYING_TO_UPDATE_VALUE + x);
        repository.updateDatabase(x);
        results.add(NESTED_OR_PARALLEL_TRANSACTION_UPDATES_VALUE + x);

        results.add(NESTED_OR_PARALLEL_TRANSACTION_IS_TRYING_TO_READ_VALUE);
        int nestedTransactionAfterNumber = repository.queryDatabase();
        results.add(NESTED_OR_PARALLEL_TRANSACTION_CAN_READ_VALUE + nestedTransactionAfterNumber);
    }
}
