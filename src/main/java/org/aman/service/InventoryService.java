package org.aman.service;

import org.aman.component.InventoryType;
import org.aman.exceptions.InvalidInventoryException;
import org.aman.exceptions.InvalidItemException;
import org.aman.model.Inventory;
import org.aman.model.Item;
import org.aman.model.Seller;
import org.aman.repository.InMemoryRepository;
import org.aman.repository.InventoryRepository;
import org.aman.repository.ItemRepository;

public class InventoryService {

    private final ItemRepository itemRepository = InMemoryRepository.itemRepository;
    private final InventoryRepository inventoryRepository = InMemoryRepository.inventoryRepository;


    public void addItem(String inventoryId , String itemId , Integer quantity){

        Item item = itemRepository.findById(itemId);
        Inventory inventory = inventoryRepository.findById(inventoryId);

        if(item!=null){
            if(InventoryType.internalInventory.equals(inventory.getInventoryType())){

                inventoryRepository.addItemToInventory(inventory.getId(),item.getId(),quantity);
                System.out.printf("%s %s has been added to flipkart inventory \n" , quantity , item.getName());
            }
            else {
                throw new InvalidInventoryException("flipkart don't maintain inventory of external seller");
            }
        }
        else{
            throw new InvalidItemException("invalid itemId");
        }

    }

    public Integer getAvailableInventory(String itemId , Seller seller){
        return inventoryRepository.getQuantityOfItem(seller.getInventoryId(),itemId);
    }
}
