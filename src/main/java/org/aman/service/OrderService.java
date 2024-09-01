package org.aman.service;

import org.aman.Util.Util;
import org.aman.component.OrderState;
import org.aman.component.ReservedInventory;
import org.aman.exceptions.ItemsFromDifferentSellerException;
import org.aman.model.Inventory;
import org.aman.model.Item;
import org.aman.model.Order;
import org.aman.repository.InMemoryRepository;
import org.aman.repository.InventoryRepository;
import org.aman.repository.OrderRepository;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class OrderService {

    private static final OrderRepository orderRepository = InMemoryRepository.orderRepository;
    public static final InventoryRepository inventoryRepository = InMemoryRepository.inventoryRepository;

    ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);



    public Order makeOrder(String customerId , Map<Item,Integer> purchasedItemWithCount,String inventoryId , String address){


        if(Util.checkALlItemsAreFromTheSameSeller(purchasedItemWithCount)) {
            Inventory inventory = inventoryRepository.findById(inventoryId);

            Order order = new Order();
            order.setId(String.valueOf(UUID.randomUUID()));
            order.setCustomerId(customerId);
            order.setOrderState(OrderState.created);
            order.setItemsOrdered(purchasedItemWithCount);
            order.setDeliveryAddress(address);
            order.setCreatedAt(new Date());


            double totalCost = 0d;
            for (var entry : purchasedItemWithCount.entrySet()) {
                Item item = entry.getKey();

                totalCost += entry.getValue() * item.getPrice();
                inventoryRepository.removeItemFromInventory(inventory.getId(), item.getId(), entry.getValue());
                ReservedInventory.addItemToReservedInventory(entry.getKey(),entry.getValue());
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

        switch (orderState){
            case cancelled -> {

                if(order.getOrderState().equals(OrderState.created)){
                    order.setOrderState(orderState);
                    // updating inventory
                    for( var entry: itemsOrdered.entrySet()){
                        ReservedInventory.moveItemsBackToInventory(entry.getKey(), entry.getValue());
                        inventoryRepository.addItemToInventory(entry.getKey().getInventoryId(),entry.getKey().getId(),entry.getValue());
                    }
                    System.out.printf("order %s has been canceled \n",order.getId());
                }
                else{
                    throw new IllegalStateException("invalid order state update");
                }
            }
            case confirmed -> {
                if(order.getOrderState().equals(OrderState.created)){
                    System.out.printf("order %s is confirmed \n",order.getId());
                    ReservedInventory.removeItemsForDelivery(itemsOrdered);
                    order.setOrderState(orderState);
                }
                else{
                    throw new IllegalStateException("invalid order state update");
                }

            }
            case delivered ->{
                if(order.getOrderState().equals(OrderState.confirmed)){
                    System.out.printf("order %s is delivered \n",order.getId());
                    order.setOrderState(orderState);
                }
                else{
                    throw new IllegalStateException("invalid order state update");
                }

            }
            case null, default -> {
                throw new IllegalStateException("invalid order state provided");
            }

        }
       return orderRepository.save(order);
    }

    // cron job to check item which is yet not confirmed after 1 min, removed from block state.
    public void startLoop(){
        executorService.scheduleAtFixedRate(() -> {
            Date threeMinAgo = Date.from(Instant.now().minusSeconds(10));
            List<Order> orders = orderRepository.findByOrderStateAndCreatedAtLessThan(OrderState.created, threeMinAgo);
            orders.forEach( order -> updateOrder(order.getId() , OrderState.cancelled));
        } , 0,10, TimeUnit.SECONDS);
    }


}
