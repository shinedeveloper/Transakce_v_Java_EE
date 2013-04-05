package net.sevecek.transactions.service;

import java.util.*;
import javax.annotation.*;
import javax.ejb.*;
import javax.transaction.*;
import net.sevecek.transactions.repository.*;

@Stateful(name = "multipleRequestBean")
@TransactionManagement(TransactionManagementType.BEAN)
public class MultipleRequestService {

    @Resource
    UserTransaction userTransaction;

    JdbcRepository repository;

    private int baseValue;


    @PostConstruct
    public void init() {
        repository = new JdbcRepository();
    }

    @Remove
    public void close() {
        try {
            userTransaction.rollback();
        } catch (SystemException e) {
            throw new RuntimeException(e);
        }
    }


    public void firstOperation(int baseValue, Collection<String> results) throws Exception {
        this.baseValue = baseValue;
        userTransaction.begin();
        results.add("UserTransaction.begin()");

        int value = baseValue + 1;
        results.add("Trying to update the DB to " + value);
        repository.updateDatabase(value, results);
        results.add("DB updated to " + value);

        results.add("Trying to read the value from the DB");
        int newValue = repository.queryDatabase(results);
        results.add("DB contains " + newValue);

//        results.add("UserTransaction.commit()");
//        userTransaction.commit();
    }


    public void secondOperation(Collection<String> results) throws Exception {
//        userTransaction.begin();
//        results.add("UserTransaction.begin()");

        results.add("Trying to read the value from the DB");
        int oldValue = repository.queryDatabase(results);
        results.add("DB contains " + oldValue);

        int value = baseValue + 5;
        results.add("Trying to update the DB to " + value);
        repository.updateDatabase(value, results);
        results.add("DB updated to " + value);

        results.add("Trying to read the value from the DB");
        int newValue = repository.queryDatabase(results);
        results.add("DB contains " + newValue);

        results.add("UserTransaction.commit()");
        userTransaction.commit();
    }

}
