package net.sevecek.customers.dao;

import java.util.*;
import javax.persistence.*;
import net.sevecek.customers.entity.*;

public class JpaCustomerDao implements CustomerDao {

    private EntityManagerFactory entityManagerFactory;


    public JpaCustomerDao(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    //----------------------------------------------------

    public List<Customer> getAllCustomers() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            TypedQuery<Customer> query = entityManager.createNamedQuery(
                    "getAllCustomers", Customer.class);
            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }


    public Customer getCustomer(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            Customer result = entityManager.find(Customer.class, id);
            return result;
        } finally {
            entityManager.close();
        }
    }


    public Customer addCustomer(Customer customer) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(customer);
            entityManager.getTransaction().commit();
            return customer;
        } finally {
            entityManager.close();
        }
    }


    public Customer updateCustomer(Customer customer) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Customer result = entityManager.merge(customer);
            entityManager.getTransaction().commit();
            return result;
        } finally {
            entityManager.close();
        }
    }


    public Customer deleteCustomer(Customer customer) {
        customer.setDeleted(true);
        return updateCustomer(customer);
    }
}
