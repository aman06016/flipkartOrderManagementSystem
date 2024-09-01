package org.aman.service;

import org.aman.model.Item;
import org.aman.model.Inventory;
import org.aman.model.Seller;
import org.aman.repository.InMemoryRepository;
import org.aman.repository.InventoryRepository;
import org.aman.repository.ItemRepository;

public class ItemService {

    private final ItemRepository itemRepository = InMemoryRepository.itemRepository;
    private final InventoryRepository inventoryRepository = InMemoryRepository.inventoryRepository;

    public Item  registerItem(String id, String name ,Integer quantity, Double price, Seller seller){
        Item item = new Item();

        item.setId(id);
        item.setName(name);
        item.setPrice(price);
        item.setSellerId(seller.getId());
        item.setInventoryId(seller.getInventoryId());
        item = itemRepository.save(item);
        inventoryRepository.addItemToInventory(item,seller,quantity);

        System.out.printf(" %s  has been added to %s's inventory \n" , item , seller.getName());
        return item;
    }
}
