package org.solthoth.java.implementing_dao;

import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCExecutor {
    public static void main (String... args) {
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost", "hplussport", "postgres", "password");
        try {
            Connection connection = dcm.getConnection();
            CustomerDAO customerDAO = new CustomerDAO(connection);
            var customer = createNewCustomer();
            customerDAO.create(customer);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static @NotNull Customer createNewCustomer() {
        var customer = new Customer();
        customer.setFirstName("Speaker");
        customer.setLastName("Man");
        customer.setEmail("speaker.man@gmail.com");
        customer.setPhone("555-555-6565");
        customer.setAddress("123 Fake St");
        customer.setCity("Playtown");
        customer.setState("CA");
        customer.setZipCode("94524");
        return customer;
    }
}