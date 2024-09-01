package org.aman.repository;

import org.aman.model.Customer;
import org.aman.model.Order;

import java.util.HashMap;
import java.util.Map;

public class OrderRepository {

    private final Map<String , Order> mapOfIdToOrder;

    public OrderRepository(){
        mapOfIdToOrder = new HashMap<>();
    }

    public Order findById(String id){
        return mapOfIdToOrder.get(id);
    }
    public Order save(Order order){
        mapOfIdToOrder.put(order.getId(),order);
        return order;
    }
}
