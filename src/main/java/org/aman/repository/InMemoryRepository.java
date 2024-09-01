package org.aman.repository;

import org.aman.model.Order;
import org.aman.service.*;

public class InMemoryRepository {

    public static final CustomerRepository customerRepository = new CustomerRepository();
    public static final ItemRepository itemRepository = new ItemRepository();
    public static final SellerRepository sellerRepository = new SellerRepository();
    public static final OrderRepository orderRepository = new OrderRepository();
    public static final InventoryRepository inventoryRepository = new InventoryRepository();


    public static final ItemService itemService = new ItemService();
    public static final CustomerService customerService = new CustomerService();
    public static final OrderService orderService = new OrderService();
    public static final SellerService sellerService = new SellerService();
    public static final InventoryService inventoryService = new InventoryService();


}
