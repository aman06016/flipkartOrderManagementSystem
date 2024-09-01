package org.aman.repository;

import org.aman.component.OrderState;
import org.aman.exceptions.InvalidOrderException;
import org.aman.model.Customer;
import org.aman.model.Order;

import java.util.*;
import java.util.stream.Collectors;

public class OrderRepository {

    private final Map<String , Order> mapOfIdToOrder;

    public OrderRepository(){
        mapOfIdToOrder = new HashMap<>();
    }

    public Order findById(String id){

        Order order =  mapOfIdToOrder.get(id);
        if(order== null)
            throw new InvalidOrderException("invalid orderId");
        return order;

    }
    public Order save(Order order){

        mapOfIdToOrder.put(order.getId(),order);
        return order;

    }


    public List<Order> findByOrderStateAndCreatedAtLessThan(OrderState orderState, Date threeMinAgo) {
        List<Order> orders =  mapOfIdToOrder.values().stream()
                .filter(x-> OrderState.created.equals(x.getOrderState())
                        && x.getCreatedAt().getTime()<threeMinAgo.getTime())
                .toList();
        return orders;
    }
}
