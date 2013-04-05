package net.sevecek.customers;

import java.util.*;
import javax.persistence.*;
import net.sevecek.customers.dao.*;
import net.sevecek.customers.entity.*;

public class ConsoleMain {

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory(
                        "VideoBoss-PersistenceUnit");

        CustomerDao customerDao =
                new JpaCustomerDao(entityManagerFactory);

        List<Customer> allCustomers = customerDao.getAllCustomers();
        for (Customer cust : allCustomers) {
            TextConsole.println(cust);
        }
    }

}
