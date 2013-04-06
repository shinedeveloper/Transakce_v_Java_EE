package net.sevecek.transactions.service;

import java.util.*;
import org.springframework.transaction.annotation.*;
import org.springframework.transaction.interceptor.*;
import net.sevecek.transactions.repository.*;

import static net.sevecek.transactions.util.Resources.*;

public class TopLevelTransactionBean {

    NestedTransactionBean nestedTransactionBean;
    JdbcRepository repository;


    public TopLevelTransactionBean(NestedTransactionBean nestedTransactionBean, JdbcRepository repository) {
        this.nestedTransactionBean = nestedTransactionBean;
        this.repository = repository;
    }


    @Deprecated
    protected TopLevelTransactionBean() {
        // This is here only for a Spring proxy. Do not call explicitly!
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void performTopLevelOperationAndRollback(int value, Collection<String> results) {
        results.add(BEGIN_TOP_LEVEL_TRANSACTION);

        int topLevelTransactionBeforeNumber = value + 1;
        repository.updateDatabase(topLevelTransactionBeforeNumber);
        results.add(TOP_LEVEL_TRANSACTION_JUST_UPDATED_VALUE + topLevelTransactionBeforeNumber);

        nestedTransactionBean.performNestedOperationAndCommit(value + 5, results);

        results.add(TOP_LEVEL_TRANSACTION_IS_TRYING_TO_READ_VALUE);
        int topLevelTransactionAfterNumber = repository.queryDatabase();
        results.add(TOP_LEVEL_TRANSACTION_CAN_READ_VALUE + topLevelTransactionAfterNumber);

        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        results.add(ROLLBACK_TOP_LEVEL_TRANSACTION);
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void performTopLevelOperationAndCommit(int value, Collection<String> results) {
        results.add(BEGIN_TOP_LEVEL_TRANSACTION);

        int topLevelTransactionBeforeNumber = value + 1;
        repository.updateDatabase(topLevelTransactionBeforeNumber);
        results.add(TOP_LEVEL_TRANSACTION_JUST_UPDATED_VALUE + topLevelTransactionBeforeNumber);

        nestedTransactionBean.performNestedOperationAndRollback(value + 5, results);

        int topLevelTransactionAfterNumber = repository.queryDatabase();
        results.add(TOP_LEVEL_TRANSACTION_CAN_READ_VALUE + topLevelTransactionAfterNumber);

        results.add(COMMIT_TOP_LEVEL_TRANSACTION);
    }

}
