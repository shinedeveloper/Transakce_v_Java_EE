package net.sevecek.videoboss.entity;

import java.io.*;
import javax.persistence.*;

@Entity
@Table(name = "Customers")
@NamedQuery(name = "findAllCustomers",
            query = "select c from Customer c where c.deleted = false")
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

    //----------------------------------------------------------------

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

    //----------------------------------------------------------------

    public Long getId() {
        return id;
    }


    public void setId(long customerId) {
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

    //----------------------------------------------------------------

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (this.getId() == null) return false;
        if (!this.testInstanceOf(other)) return false;
        Customer otherCustomer = (Customer) other;
        if (!otherCustomer.testInstanceOf(this)) return false;

        return getId().equals(otherCustomer.getId());
    }


    protected boolean testInstanceOf(Object other) {
        return other instanceof Customer;
    }


    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }


    @Override
    public String toString() {
        return "Customer[" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", deleted=" + deleted +
                ']';
    }

}
