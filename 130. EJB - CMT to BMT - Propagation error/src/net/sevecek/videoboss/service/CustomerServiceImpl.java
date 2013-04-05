package net.sevecek.videoboss.service;

import java.util.*;
import javax.ejb.*;
import javax.ejb.Singleton;
import javax.inject.*;
import net.sevecek.videoboss.dao.*;
import net.sevecek.videoboss.entity.*;
import static javax.ejb.TransactionManagementType.*;


@Singleton(name = "customerService")
@Local(CustomerService.class)
@TransactionManagement(BEAN)
public class CustomerServiceImpl implements CustomerService {

    @EJB
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
        Customer result = customerDao.updateCustomer(customer);
        return result;
    }


    public Customer deleteCustomer(Customer customer) {
        Customer c = customerDao.findCustomer(customer.getId());
        c.setVersion(customer.getVersion());
        return customerDao.deleteCustomer(customer);
    }

}
