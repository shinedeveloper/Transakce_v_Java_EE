package net.sevecek.videoboss.service;

import java.util.*;
import javax.inject.*;
import org.springframework.transaction.*;
import org.springframework.transaction.annotation.*;
import net.sevecek.videoboss.entity.*;

@Named("serviceFacade")
public class ServiceFacade {

    @Inject
    CustomerService customerService;


    @Transactional
    public List<Customer> findAllCustomers() {
        List<Customer> result = customerService.findAllCustomers();
        return result;
    }


    @Transactional
    public Customer findCustomer(long id) {
        Customer result = customerService.findCustomer(id);
        return result;
    }


    @Transactional
    public Customer updateCustomer(Customer customer) {
        Customer result = customerService.updateCustomer(customer);
        return result;
    }


    @Transactional
    public Customer addCustomer(Customer customer) {
        Customer result = customerService.addCustomer(customer);
        return result;
    }


    @Transactional
    public Customer deleteCustomer(Customer customer) {
        Customer result = customerService.deleteCustomer(customer);
        return result;
    }
}
