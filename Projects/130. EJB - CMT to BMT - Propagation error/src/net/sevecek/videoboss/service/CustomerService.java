package net.sevecek.videoboss.service;


import net.sevecek.videoboss.entity.Customer;
import java.util.List;


public interface CustomerService {

    List<Customer> findAllCustomers();

    Customer findCustomer(long id);

    Customer updateCustomer(Customer customer);

    Customer addCustomer(Customer customer);

    Customer deleteCustomer(Customer customer);

}
