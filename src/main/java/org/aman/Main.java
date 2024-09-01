package org.aman;


import org.aman.component.InventoryType;
import org.aman.component.OrderState;
import org.aman.model.Customer;
import org.aman.model.Item;
import org.aman.model.Order;
import org.aman.model.Seller;
import org.aman.repository.InMemoryRepository;
import org.aman.service.*;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {


        InMemoryRepository inMemoryRepository = new InMemoryRepository();

        SellerService sellerService = InMemoryRepository.sellerService;
        ItemService itemService = InMemoryRepository.itemService;
        CustomerService customerService = InMemoryRepository.customerService;
        OrderService orderService = InMemoryRepository.orderService;
        InventoryService inventoryService = InMemoryRepository.inventoryService;


        // register seller
        Seller flipkart = sellerService.registerSeller("1" , "flipkart" ,"flipkart@mail.com", InventoryType.internalInventory);
        Seller rahulSeller = sellerService.registerSeller("2" , "rahul", "rahul@mail.com",InventoryType.externalInventory);

        // register item
        Item chair = itemService.registerItem("1","chair" , 3,10d , flipkart);
        Item table = itemService.registerItem("2","table" , 4,20d,flipkart);
        Item pen = itemService.registerItem("3","pen",10,10d,rahulSeller);

        // add item to inventory
        inventoryService.addItem(flipkart.getInventoryId() , chair.getId(),4);
        //inventoryService.addItem(rahulSeller.getInventoryId() , pen.getId(),10);
        inventoryService.addItem(flipkart.getInventoryId(), pen.getId(),10);

        // get available inventory
        Integer itemCount= inventoryService.getAvailableInventory(pen.getId(), rahulSeller);


        // register customer
        Customer amanCustomer = customerService.registerCustomer("1","aman","aman@mail.com","noida");

        // make a order
        //Order order = orderService.makeOrder(amanCustomer.getId(), Map.of(pen,3,chair,2));
        Order order = orderService.makeOrder(amanCustomer.getId(), Map.of(table,3,chair,2),flipkart.getInventoryId());
        System.out.printf("total cost to pay %s for orderId %s \n",order.getTotalCost(),order.getId());
        // update a order
        order = orderService.updateOrder(order.getId(), OrderState.canceled);





    }
}