package net.sevecek.videoboss.service;

import java.util.*;
import javax.inject.*;
import net.sevecek.videoboss.dao.*;
import net.sevecek.videoboss.entity.*;

@Named("customerService")
public class CustomerServiceImpl implements CustomerService {

    @Inject
    CustomerDao customerDao;


    public Customer findCustomer(long id) {
        return customerDao.findCustomer(id);
    }


    public List<Customer> findAllCustomers() {
        return customerDao.findAllCustomers();
    }


    public Customer addCustomer(Customer customer) {
        return customerDao.addCustomer(customer);
    }


    public Customer updateCustomer(Customer customer) {
        return customerDao.updateCustomer(customer);
    }


    public Customer deleteCustomer(Customer customer) {
        return customerDao.deleteCustomer(customer);
    }

}
