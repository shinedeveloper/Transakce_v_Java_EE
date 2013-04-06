package net.sevecek.videoboss.service;

import java.util.*;
import javax.inject.*;
import org.springframework.transaction.*;
import org.springframework.transaction.support.*;
import net.sevecek.videoboss.entity.*;

@Named("serviceFacade")
public class ServiceFacade {

    @Inject
    PlatformTransactionManager transactionManager;

    @Inject
    CustomerService customerService;


    public List<Customer> findAllCustomers() {
        TransactionStatus tx = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            List<Customer> result = customerService.findAllCustomers();

            transactionManager.commit(tx);
            return result;
        } catch (RuntimeException ex) {
            transactionManager.rollback(tx);
            throw ex;
        }
    }


    public Customer findCustomer(long id) {
        TransactionStatus tx = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            Customer result = customerService.findCustomer(id);

            transactionManager.commit(tx);
            return result;
        } catch (RuntimeException ex) {
            transactionManager.rollback(tx);
            throw ex;
        }
    }


    public Customer updateCustomer(Customer customer) {
        TransactionStatus tx = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            Customer result = customerService.updateCustomer(customer);

            transactionManager.commit(tx);
            return result;
        } catch (RuntimeException ex) {
            transactionManager.rollback(tx);
            throw ex;
        }
    }


    public Customer addCustomer(Customer customer) {
        TransactionStatus tx = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            Customer result = customerService.addCustomer(customer);

            transactionManager.commit(tx);
            return result;
        } catch (RuntimeException ex) {
            transactionManager.rollback(tx);
            throw ex;
        }
    }


    public Customer deleteCustomer(Customer customer) {
        TransactionStatus tx = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            Customer result = customerService.deleteCustomer(customer);

            transactionManager.commit(tx);
            return result;
        } catch (RuntimeException ex) {
            transactionManager.rollback(tx);
            throw ex;
        }
    }
}
