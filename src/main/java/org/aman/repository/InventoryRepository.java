package org.aman.repository;

import org.aman.component.InventoryType;
import org.aman.exceptions.InvalidInventoryException;
import org.aman.exceptions.NotEnoughInventoryException;
import org.aman.model.Inventory;
import org.aman.model.Item;
import org.aman.model.Seller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InventoryRepository {


    private final Map<String , Inventory> mapOfIdToInventory;
    private final Object lock = new Object();

    public InventoryRepository(){

        mapOfIdToInventory = new HashMap<>();
    }


    public Inventory findById(String id){
        Inventory inventory =  mapOfIdToInventory.get(id);
        if(inventory == null){
            throw new InvalidInventoryException("invalid inventory exception");
        }
        return inventory;
    }

    public Integer getQuantityOfItem(String inventoryId , String itemId){
        if(mapOfIdToInventory.get(inventoryId)!=null){
            return mapOfIdToInventory.get(inventoryId).getMapOfItemIdToQuantity().get(itemId);
        }
        else{
            throw new IllegalArgumentException("invalid inventoryId");
        }

    }


    public Inventory createInventoryForTheSeller(Seller seller,InventoryType inventoryType) {
        Inventory inventory = new Inventory();
        inventory.setId(String.valueOf(UUID.randomUUID()));
        inventory.setInventoryType(inventoryType);
        inventory.setSellerId(seller.getId());

        mapOfIdToInventory.put(inventory.getId(),inventory);
        System.out.printf("current state of inventory now %s \n", inventory);
        return inventory;
    }


    public void addItemToInventory(Item item, Seller seller,Integer quantity) {

        Object lock = Inventory.getLockForPerticularItem(item.getId());
        synchronized (lock){
            Inventory inventory = mapOfIdToInventory.get(seller.getInventoryId());

            inventory.getMapOfItemIdToQuantity().computeIfAbsent(item.getId(), x->0);
            inventory.getMapOfItemIdToQuantity()
                    .put(item.getId(), inventory.getMapOfItemIdToQuantity().get(item.getId()) +quantity);

            System.out.printf("current state of inventory now %s \n", inventory);
        }

    }
    public void addItemToInventory(String inventoryId ,String itemId ,  Integer quantity){

        Object lock = Inventory.getLockForPerticularItem(itemId);
        synchronized (lock){
            Inventory inventory = mapOfIdToInventory.get(inventoryId);
            inventory.getMapOfItemIdToQuantity().computeIfAbsent(itemId, x->0);
            inventory.getMapOfItemIdToQuantity()
                    .put(itemId, inventory.getMapOfItemIdToQuantity().get(itemId) +quantity);

            System.out.printf("current state of inventory now %s \n", inventory);
        }

    }

    public void removeItemFromInventory(String inventoryId , String itemId , Integer quantity){
        Inventory inventory = mapOfIdToInventory.get(inventoryId);

        Object lock = Inventory.getLockForPerticularItem(itemId);
        synchronized (lock){
            if(inventory.getMapOfItemIdToQuantity().get(itemId) >= quantity){
                inventory.getMapOfItemIdToQuantity()
                        .put(itemId, inventory.getMapOfItemIdToQuantity().get(itemId) -quantity);
                System.out.printf("current state of inventory now %s \n", inventory);
            }else{
                throw new NotEnoughInventoryException("not enough inventory to to fulfill this order ");
            }
        }

    }
}
