package org.solthoth.java.implementing_dao;

import org.solthoth.java.implementing_dao.util.DataAccessObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
}
