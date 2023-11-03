package org.solthoth.java.implementing_dao;

import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

public class JDBCExecutor {
    public static void main (String... args) {
        var flag = "DELETE";
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost", "hplussport", "postgres", "password");
        try {
            Connection connection = dcm.getConnection();
            CustomerDAO customerDAO = new CustomerDAO(connection);
            if (flag.equals("CREATE")) {
                var customer = createNewCustomer();
                var insertedCustomer = customerDAO.create(customer);
                System.out.println(insertedCustomer);
            } else if (flag.equals("READ")){
                var customer = customerDAO.findById(10001);
                System.out.println(String.format("%s %s", customer.getFirstName(), customer.getLastName()));
            } else if (flag.equals("UPDATE")) {
                var customer = customerDAO.findById(10001);
                System.out.println(String.format("%s %s %s", customer.getFirstName(), customer.getLastName(), customer.getEmail()));
                customer.setEmail("sman@gmail.com");
                customer = customerDAO.update(customer);
                System.out.println(String.format("%s %s %s", customer.getFirstName(), customer.getLastName(), customer.getEmail()));
            } else if (flag.equals("DELETE")) {
                var customer = createNewCustomer();
                customer.setFirstName("Radio");
                customer.setEmail("Radio.man@gmail.com");
                var dbCustomer = customerDAO.create(customer);
                System.out.println(dbCustomer);
                dbCustomer = customerDAO.findById(dbCustomer.getId());
                System.out.println(dbCustomer);
                dbCustomer.setEmail("rman@gmail.com");
                dbCustomer = customerDAO.update(dbCustomer);
                System.out.println(dbCustomer);
                customerDAO.delete(dbCustomer.getId());
            }
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