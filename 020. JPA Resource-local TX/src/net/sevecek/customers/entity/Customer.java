package net.sevecek.customers.entity;

import java.io.*;
import java.util.*;

import javax.persistence.*;

@Entity
@Table(name = "Customers")
@NamedQuery(name = "getAllCustomers",
            query = "select c from Customer c " +
                    "where c.deleted = false")
public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private String address;

    private boolean deleted;

    @Version
    private int version;

    //------------------------------------------------------------------------

    public Customer() {
    }


    public Customer(String firstName, String lastName, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
    }


    public Customer(Long id, String firstName, String lastName, String address, boolean deleted, int version) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.deleted = deleted;
        this.version = version;
    }


    public Long getId() {
        return id;
    }


    public void setId(Long customerId) {
        this.id = customerId;
    }


    public String getFirstName() {
        return firstName;
    }


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    public String getLastName() {
        return lastName;
    }


    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public String getAddress() {
        return address;
    }


    public void setAddress(String address) {
        this.address = address;
    }


    public boolean isDeleted() {
        return deleted;
    }


    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }


    public int getVersion() {
        return version;
    }


    public void setVersion(int version) {
        this.version = version;
    }

    //------------------------------------------------------------------------

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;
        Customer otherCustomer = (Customer) o;
        if (!otherCustomer.reverseInstanceOf(this)) return false;
        if (id == null) return false;

        return id.equals(otherCustomer.id);
    }


    protected boolean reverseInstanceOf(Object other) {
        return other instanceof Customer;
    }


    @Override
    public int hashCode() {
        if (id == null) return super.hashCode();
        return id.hashCode();
    }


    public String toString() {
        return "Customer[" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", deleted=" + deleted +
                ", version=" + version +
                ']';
    }

}
