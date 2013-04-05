package net.sevecek.videoboss.dao;


import net.sevecek.videoboss.entity.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Repository;
import java.util.*;
import javax.ejb.*;
import javax.ejb.Singleton;
import javax.inject.*;
import javax.persistence.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;


@Singleton(name = "customerDao")
public class JpaCustomerDao implements CustomerDao {

    @PersistenceContext
    private EntityManager entityManager;


    public Customer findCustomer(long id) {
        return entityManager.find(Customer.class, id);
    }


    public List<Customer> findAllCustomers() {
        TypedQuery<Customer> query = entityManager.createNamedQuery(
                "findAllCustomers", Customer.class);
        return query.getResultList();
    }


    public Customer addCustomer(Customer customer) {
        customer.setDeleted(false);
        entityManager.persist(customer);
        return customer;
    }


    public Customer updateCustomer(Customer customer) {
        return entityManager.merge(customer);
    }


    public Customer deleteCustomer(Customer customer) {
        customer.setDeleted(true);
        return updateCustomer(customer);
    }
}
