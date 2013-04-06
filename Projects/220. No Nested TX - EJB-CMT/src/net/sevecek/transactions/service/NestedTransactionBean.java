package net.sevecek.transactions.service;

import java.util.*;
import javax.annotation.*;
import javax.ejb.*;
import net.sevecek.transactions.repository.*;

import static net.sevecek.transactions.util.Resources.*;

@Singleton(name = "nestedTransactionBean")
public class NestedTransactionBean {

    @EJB
    JdbcRepository repository;

    @Resource
    SessionContext ejbContext;


    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void performNestedOperationAndCommit(int x, Collection<String> results) {
        results.add(BEGIN_NESTED_TRANSACTION);

        performOperation(x, results);

        results.add(COMMIT_NESTED_TRANSACTION);
    }


    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void performNestedOperationAndRollback(int x, Collection<String> results) {
        results.add(BEGIN_NESTED_TRANSACTION);

        results.add(NESTED_OR_PARALLEL_TRANSACTION_IS_TRYING_TO_READ_VALUE);
        int nestedTransactionAfterNumber = repository.queryDatabase();
        results.add(NESTED_OR_PARALLEL_TRANSACTION_CAN_READ_VALUE + nestedTransactionAfterNumber);

        ejbContext.setRollbackOnly();
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
