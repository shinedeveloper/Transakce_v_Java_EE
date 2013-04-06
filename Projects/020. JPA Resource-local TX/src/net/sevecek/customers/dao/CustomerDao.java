package net.sevecek.customers.dao;


import net.sevecek.customers.entity.Customer;
import java.util.List;


public interface CustomerDao {

    List<Customer> getAllCustomers();

    Customer getCustomer(Long id);

    Customer addCustomer(Customer customer);

    Customer updateCustomer(Customer customer);

    Customer deleteCustomer(Customer customer);

}
