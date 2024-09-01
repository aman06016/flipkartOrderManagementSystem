package org.aman.model;

import org.aman.component.OrderState;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class Order {
    private String id;
    private String customerId;
    private Map<Item,Integer> itemsOrdered;
    private Double totalCost;
    private OrderState orderState;
    private String deliveryAddress ;
    private Date createdAt ;

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", customerId='" + customerId + '\'' +
                ", itemsOrdered=" + itemsOrdered +
                ", totalCost=" + totalCost +
                ", orderState=" + orderState +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<Item, Integer> getItemsOrdered() {
        return itemsOrdered;
    }

    public void setItemsOrdered(Map<Item, Integer> itemsOrdered) {
        this.itemsOrdered = itemsOrdered;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public OrderState getOrderState() {
        return orderState;
    }

    public void setOrderState(OrderState orderState) {
        this.orderState = orderState;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }
}
