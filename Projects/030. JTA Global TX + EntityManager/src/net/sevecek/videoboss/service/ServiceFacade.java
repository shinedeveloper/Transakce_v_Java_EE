package net.sevecek.videoboss.service;

import java.util.*;
import javax.transaction.*;
import net.sevecek.videoboss.entity.*;
import net.sevecek.videoboss.util.*;
import static net.sevecek.videoboss.util.ExceptionUtils.rethrowAsUnchecked;

public class ServiceFacade {

    private TransactionManager transactionManager;
    private CustomerService customerService;


    public ServiceFacade(TransactionManager transactionManager, CustomerService customerService) {
        this.transactionManager = transactionManager;
        this.customerService = customerService;
    }


    public List<Customer> findAllCustomers() {
        try {
            transactionManager.begin();
            try {
                List<Customer> result = customerService.findAllCustomers();

                transactionManager.commit();
                return result;
            } catch (RuntimeException ex) {
                transactionManager.rollback();
                throw ex;
            }
        } catch (Exception e) {
            throw rethrowAsUnchecked(e);
        }
    }


    public Customer findCustomer(long id) {
        try {
            transactionManager.begin();
            try {
                Customer result = customerService.findCustomer(id);

                transactionManager.commit();
                return result;
            } catch (RuntimeException ex) {
                transactionManager.rollback();
                throw ex;
            }
        } catch (Exception e) {
            throw rethrowAsUnchecked(e);
        }
    }


    public Customer updateCustomer(Customer customer) {
        try {
            transactionManager.begin();
            try {
                Customer result = customerService.updateCustomer(customer);

                transactionManager.commit();
                return result;
            } catch (RuntimeException ex) {
                transactionManager.rollback();
                throw ex;
            }
        } catch (Exception e) {
            throw rethrowAsUnchecked(e);
        }
    }


    public Customer addCustomer(Customer customer) {
        try {
            transactionManager.begin();
            try {
                Customer result = customerService.addCustomer(customer);

                transactionManager.commit();
                return result;
            } catch (RuntimeException ex) {
                transactionManager.rollback();
                throw ex;
            }
        } catch (Exception e) {
            throw rethrowAsUnchecked(e);
        }
    }


    public Customer deleteCustomer(Customer customer) {
        try {
            transactionManager.begin();
            try {
                Customer result = customerService.deleteCustomer(customer);

                transactionManager.commit();
                return result;
            } catch (RuntimeException ex) {
                transactionManager.rollback();
                throw ex;
            }
        } catch (Exception e) {
            throw rethrowAsUnchecked(e);
        }
    }
}