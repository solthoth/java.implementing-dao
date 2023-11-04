package org.solthoth.java.implementing_dao;

import org.solthoth.java.implementing_dao.util.DataAccessObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderDAO extends DataAccessObject<Order> {
    private static final String GET_ONE = """
            SELECT
                c.first_name customerFName, c.last_name customerLName, c.email customerEmail, o.order_id,
                o.creation_date, o.total_due, o.status,
                s.first_name salesFName, s.last_name salesLName, s.email salesEmail,
                ol.quantity, p.code, p.name, p.size, p.variety, p.price
            FROM orders o
                join customer c on o.customer_id=c.customer_id
                join salesperson s on o.salesperson_id=s.salesperson_id
                join order_item ol on ol.order_id=o.order_id
                join product p on ol.product_id = p.product_id
            WHERE o.order_id=?
            """;
    private static final String GET_FOR_CUST = "SELECT * FROM get_orders_by_customer(?)";
    public OrderDAO(Connection connection) {
        super(connection);
    }

    @Override
    public Order findById(long id) {
        var order = new Order();
        try(PreparedStatement statement = this.connection.prepareStatement(GET_ONE)){
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            if(rs.next()) {
                order.setCustomerFirstName(rs.getString("customerFName"));
                order.setCustomerLastName(rs.getString("customerLName"));
                order.setCustomerEmail(rs.getString("customerEmail"));
                order.setId(rs.getLong("order_id"));
                order.setCreationDate(new Date(rs.getDate("creation_date").getTime()));
                order.setTotalDue(rs.getBigDecimal("total_due"));
                order.setStatus(rs.getString("status"));
                order.setSalesPersonFirstName(rs.getString("salesFName"));
                order.setSalesPersonLastName(rs.getString("salesLName"));
                order.setSalesPersonEmail(rs.getString("salesEmail"));
                do {
                    var items = order.getItems();
                    var item = new OrderItem();
                    item.setQuantity(rs.getInt("quantity"));
                    item.setProductCode(rs.getString("code"));
                    item.setProductName(rs.getString("name"));
                    item.setProductSize(rs.getInt("size"));
                    item.setProductVariety(rs.getString("variety"));
                    item.setProductPrice(rs.getBigDecimal("price"));
                    items.add(item);
                }while(rs.next());
            }
        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return order;
    }

    @Override
    public List<Order> findAll() {
        return null;
    }

    @Override
    public Order update(Order dto) {
        return null;
    }

    @Override
    public Order create(Order dto) {
        return null;
    }

    @Override
    public void delete(long id) {

    }

    public List<Order> getOrdersForCustomer(long customerId) {
        var orders = new ArrayList<Order>();
        try(PreparedStatement statement = this.connection.prepareStatement(GET_FOR_CUST)) {
            statement.setLong(1, customerId);
            ResultSet rs = statement.executeQuery();
            long orderId = 0;
            Order order = null;
            while(rs.next()){
                var localOrderId = rs.getLong(4);
                if(orderId != localOrderId) {
                    order = new Order();
                    orders.add(order);
                    order.setId(localOrderId);
                    orderId = localOrderId;
                    order.setCustomerFirstName(rs.getString(1));
                    order.setCustomerLastName(rs.getString(2));
                    order.setCustomerEmail(rs.getString(3));
                    order.setCreationDate(new Date(rs.getDate(5).getTime()));
                    order.setTotalDue(rs.getBigDecimal(6));
                    order.setStatus(rs.getString(7));
                    order.setSalesPersonFirstName(rs.getString(8));
                    order.setSalesPersonLastName(rs.getString(9));
                    order.setSalesPersonEmail(rs.getString(10));
                }
                var orderLine = new OrderItem();
                orderLine.setQuantity(rs.getInt(11));
                orderLine.setProductCode(rs.getString(12));
                orderLine.setProductName(rs.getString(13));
                orderLine.setProductSize(rs.getInt(14));
                orderLine.setProductVariety(rs.getString(15));
                orderLine.setProductPrice(rs.getBigDecimal(16));
                order.getItems().add(orderLine);
            }
        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return orders;
    }
}
