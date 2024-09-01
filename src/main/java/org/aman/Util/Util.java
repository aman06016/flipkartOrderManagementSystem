package org.aman.Util;

import org.aman.model.Inventory;
import org.aman.model.Item;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Util {

    public static Boolean checkALlItemsAreFromTheSameSeller(Map<Item,Integer> itemsWithCount){

        var x =itemsWithCount.keySet().stream().map(Item::getSellerId).collect(Collectors.toSet());

        return x.size() == 1 ;
    }

    public static boolean checkAllItemsAreAvailableInInventory(Map<Item,Integer> itemsWithCount, Inventory inventory) {

        var entries = itemsWithCount.entrySet().stream().filter(entry ->
                inventory.getMapOfItemIdToQuantity().get(entry.getKey().getId()) < entry.getValue()).toList();
        return entries.isEmpty();
    }
}
