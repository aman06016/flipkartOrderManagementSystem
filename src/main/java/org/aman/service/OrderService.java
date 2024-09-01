package org.aman.service;

import org.aman.Util.Util;
import org.aman.component.InventoryType;
import org.aman.component.OrderState;
import org.aman.exceptions.InvalidItemException;
import org.aman.exceptions.ItemsFromDifferentSellerException;
import org.aman.model.Inventory;
import org.aman.model.Item;
import org.aman.model.Order;
import org.aman.repository.InMemoryRepository;
import org.aman.repository.InventoryRepository;
import org.aman.repository.ItemRepository;
import org.aman.repository.OrderRepository;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class OrderService {

    private static final OrderRepository orderRepository = InMemoryRepository.orderRepository;
    public static final InventoryRepository inventoryRepository = InMemoryRepository.inventoryRepository;
    private final Lock lock = new ReentrantLock();
    public Order makeOrder(String customerId , Map<Item,Integer> purchasedItemWithCount,String inventoryId){


        if(Util.checkALlItemsAreFromTheSameSeller(purchasedItemWithCount)) {
            Inventory inventory = inventoryRepository.findById(inventoryId);

            Order order = new Order();
            order.setId(String.valueOf(UUID.randomUUID()));
            order.setCustomerId(customerId);
            order.setOrderState(OrderState.confirmed);
            order.setItemsOrdered(purchasedItemWithCount);


            double totalCost = 0d;
            for (var entry : purchasedItemWithCount.entrySet()) {
                Item item = entry.getKey();

                totalCost += entry.getValue() * item.getPrice();
                inventoryRepository.removeItemFromInventory(inventory.getId(), item.getId(), entry.getValue());
            }
            order.setTotalCost(totalCost);
            System.out.printf(" order successful %s \n", order);
            return orderRepository.save(order);
        }
        else{
            throw new ItemsFromDifferentSellerException("items from multiple seller are not allowed for single order");
        }


    }

    public Order updateOrder(String orderId, OrderState orderState){
        Order order = orderRepository.findById(orderId);
        Map<Item,Integer> itemsOrdered = order.getItemsOrdered();
        String inventoryId = null;
        switch (orderState){
            case canceled -> {

                order.setOrderState(orderState);
                // updating inventory
                for( var entry: itemsOrdered.entrySet()){
                    inventoryId = entry.getKey().getInventoryId();
                    inventoryRepository.addItemToInventory(entry.getKey().getInventoryId(),entry.getKey().getId(),entry.getValue());
                }
                System.out.printf("order %s has been canceled \n",order.getId());
            }
            case confirmed -> {
                System.out.printf("order %s is confirmed \n",order.getId());
                order.setOrderState(orderState);
            }
            case delivered ->{
                System.out.printf("order %s is delivered \n",order.getId());
                order.setOrderState(orderState);
            }
            case null, default -> {
                throw new IllegalStateException("invalid order state provided");
            }

        }
       return orderRepository.save(order);
    }
}
