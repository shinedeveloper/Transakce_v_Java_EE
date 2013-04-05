package net.sevecek.videoboss.service;

import java.util.*;
import javax.inject.*;
import net.sevecek.videoboss.dao.*;
import net.sevecek.videoboss.entity.*;

public class CustomerServiceImpl implements CustomerService {

    CustomerDao customerDao;


    public CustomerServiceImpl(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }


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
        Customer c = customerDao.findCustomer(customer.getId());
        c.setVersion(customer.getVersion());
        return customerDao.deleteCustomer(customer);
    }

}
