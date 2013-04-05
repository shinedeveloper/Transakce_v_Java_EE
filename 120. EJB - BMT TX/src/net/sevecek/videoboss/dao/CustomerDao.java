package net.sevecek.videoboss.dao;

import net.sevecek.videoboss.entity.Customer;
import java.util.List;

public interface CustomerDao {

    Customer findCustomer(long id);

    List<Customer> findAllCustomers();

    Customer addCustomer(Customer customer);

    Customer updateCustomer(Customer customer);

    Customer deleteCustomer(Customer customer);
}
