package net.sevecek.videoboss.service;

import java.util.*;
import javax.ejb.*;
import net.sevecek.videoboss.entity.*;

@Singleton(name = "serviceFacade")
public class ServiceFacade {

    @EJB
    private CustomerService customerService;


    @TransactionAttribute
    public List<Customer> findAllCustomers() {
        List<Customer> result = customerService.findAllCustomers();
        return result;
    }


    @TransactionAttribute
    public Customer findCustomer(long id) {
        Customer result = customerService.findCustomer(id);
        return result;
    }


    @TransactionAttribute
    public Customer updateCustomer(Customer customer) {
        Customer result = customerService.updateCustomer(customer);
        return result;
    }


    @TransactionAttribute
    public Customer addCustomer(Customer customer) {
        Customer result = customerService.addCustomer(customer);
        return result;
    }


    @TransactionAttribute
    public Customer deleteCustomer(Customer customer) {
        Customer result = customerService.deleteCustomer(customer);
        return result;
    }
}
