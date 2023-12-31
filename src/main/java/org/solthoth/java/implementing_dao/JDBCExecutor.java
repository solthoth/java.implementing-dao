package org.solthoth.java.implementing_dao;

import org.jetbrains.annotations.NotNull;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.function.Consumer;

public class JDBCExecutor {
    public static void main (String... args) {
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost", "hplussport", "postgres", "password");
        System.out.println("DAO Pattern Examples...");
        DAOPatternExamples(dcm,"NONE");
        System.out.println("DAO Challenge...");
        DAOChallenge(dcm);
        System.out.println("DAO Stored Procedures...");
        DAOStoredProc(dcm);
        System.out.println("DAO Limit and Order...");
        DAOLimitAndOrderResults(dcm);
        System.out.println("DAO Paging...");
        DAOPagingResults(dcm);
    }
    public static void DAOPatternExamples (DatabaseConnectionManager dcm, String flag) {
        ExecuteConnection(dcm, connection -> {
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
        });
    }
    public static void DAOChallenge(DatabaseConnectionManager dcm) {
        ExecuteConnection(dcm, connection -> {
            var orderDao = new OrderDAO(connection);
            var orderId = 1003;
            var order = orderDao.findById(orderId);
            System.out.println(order);
        });
    }
    public static void DAOStoredProc(DatabaseConnectionManager dcm) {
        ExecuteConnection(dcm, connection -> {
            var orderDAO = new OrderDAO(connection);
            var orders = orderDAO.getOrdersForCustomer(789);
            orders.forEach(System.out::println);
        });
    }
    public static void DAOLimitAndOrderResults(DatabaseConnectionManager dcm) {
        ExecuteConnection(dcm, connection -> {
            var customerDAO = new CustomerDAO(connection);
            customerDAO.findAllSorted(20).forEach(System.out::println);
        });
    }
    public static void DAOPagingResults(DatabaseConnectionManager dcm) {
        ExecuteConnection(dcm, connection -> {
            var customerDAO = new CustomerDAO(connection);
            customerDAO.findAllSorted(20).forEach(System.out::println);
            System.out.println("Paged");
            for(int i=1;i<3;i++){
                System.out.println("Page number: "+i);
                customerDAO.findAllPaged(10, i).forEach(System.out::println);
            }
        });
    }

    public static void ExecuteConnection(DatabaseConnectionManager dcm, Consumer<Connection> operation) {
        try(Connection connection = dcm.getConnection()){
            operation.accept(connection);
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