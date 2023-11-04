package org.solthoth.java.implementing_dao;

import org.solthoth.java.implementing_dao.util.DataTransferObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

public class Order implements DataTransferObject {
    private String customerFirstName;
    private String customerLastName;
    private String customerEmail;
    private long id;
    private Date creationDate;
    private BigDecimal totalDue;
    private String status;
    private String salesPersonFirstName;
    private String salesPersonLastName;
    private String salesPersonEmail;
    private ArrayList<OrderItem> items;

    public String getCustomerFirstName() {
        return customerFirstName;
    }

    public void setCustomerFirstName(String customerFirstName) {
        this.customerFirstName = customerFirstName;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public BigDecimal getTotalDue() {
        return totalDue;
    }

    public void setTotalDue(BigDecimal totalDue) {
        this.totalDue = totalDue;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSalesPersonFirstName() {
        return salesPersonFirstName;
    }

    public void setSalesPersonFirstName(String salesPersonFirstName) {
        this.salesPersonFirstName = salesPersonFirstName;
    }

    public String getSalesPersonLastName() {
        return salesPersonLastName;
    }

    public void setSalesPersonLastName(String salesPersonLastName) {
        this.salesPersonLastName = salesPersonLastName;
    }

    public String getSalesPersonEmail() {
        return salesPersonEmail;
    }

    public void setSalesPersonEmail(String salesPersonEmail) {
        this.salesPersonEmail = salesPersonEmail;
    }

    public ArrayList<OrderItem> getItems() {
        if(items == null){
            items = new ArrayList<OrderItem>();
        }
        return items;
    }

    public void setItems(ArrayList<OrderItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Order{" +
                "customerFirstName='" + customerFirstName + '\'' +
                ", customerLastName='" + customerLastName + '\'' +
                ", customerEmail='" + customerEmail + '\'' +
                ", id=" + id +
                ", creationDate=" + creationDate +
                ", totalDue=" + totalDue +
                ", status='" + status + '\'' +
                ", salesPersonFirstName='" + salesPersonFirstName + '\'' +
                ", salesPersonLastName='" + salesPersonLastName + '\'' +
                ", salesPersonEmail='" + salesPersonEmail + '\'' +
                ", items=" + items +
                '}';
    }
}
