package net.sevecek.videoboss.util;

import javax.naming.*;
import javax.persistence.*;
import javax.transaction.*;
import net.sevecek.videoboss.dao.*;
import net.sevecek.videoboss.service.*;

import static net.sevecek.videoboss.util.ExceptionUtils.*;

public class ServiceLocator {

    private static ServiceLocator instance = new ServiceLocator();


    public static ServiceLocator getInstance() {
        return instance;
    }

    //-----------------------------------------------------

    private ServiceFacade serviceFacade;
    private CustomerService customerService;
    private CustomerDao customerDao;
    private EntityManager containerManagedEntityManager;
    private TransactionManager transactionManager;


    protected ServiceLocator() {
    }


    public synchronized ServiceFacade getServiceFacade() {
        if (serviceFacade == null) {
            serviceFacade = new ServiceFacade(getTransactionManager(), getCustomerService());
        }
        return serviceFacade;
    }


    public synchronized CustomerService getCustomerService() {
        if (customerService == null) {
            customerService = new CustomerServiceImpl(getCustomerDao());
        }
        return customerService;
    }


    public synchronized CustomerDao getCustomerDao() {
        if (customerDao == null) {
            customerDao = new JpaCustomerDao(getContainerManagedEntityManager());
        }
        return customerDao;
    }


    public synchronized EntityManager getContainerManagedEntityManager() {
        try {
            if (containerManagedEntityManager == null) {
                Context jndiRegistry = new InitialContext();
                containerManagedEntityManager = (EntityManager)
                        jndiRegistry.lookup(
                                "java:comp/env/jpa/entityManager");

            }
            return containerManagedEntityManager;
        } catch (Exception e) {
            throw rethrowAsUnchecked(e);
        }
    }


    private synchronized TransactionManager getTransactionManager() {
        try {
            if (transactionManager == null) {
                Context jndiRegistry = new InitialContext();
                transactionManager = (TransactionManager)
                        jndiRegistry.lookup(
                                "java:appserver/TransactionManager");
            }
            return transactionManager;
        } catch (Exception e) {
            throw rethrowAsUnchecked(e);
        }
    }
}
