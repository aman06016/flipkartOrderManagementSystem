package org.aman.component;

import org.aman.model.Item;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ReservedInventory {
    private static Map<Item,Integer> reservedItems = new ConcurrentHashMap<>();



    public static void addItemToReservedInventory(Item item, Integer quantity){
        reservedItems.computeIfAbsent(item,k->0);
        reservedItems.put(item,reservedItems.get(item) + quantity);
        System.out.println("reserved inventory state = " + reservedItems.toString());

    }
    public static void moveItemsBackToInventory(Item item , Integer quantity){
        reservedItems.put(item,reservedItems.get(item)-quantity);
        System.out.println("reserved inventory state = " + reservedItems.toString());
    }

    public static void removeItemsForDelivery(Map<Item , Integer> items){
        for(var entry:items.entrySet()){
            reservedItems.put(entry.getKey(),reservedItems.get(entry.getKey()) - entry.getValue());
            System.out.println("reserved inventory state = " + reservedItems.toString());
        }
    }

}
