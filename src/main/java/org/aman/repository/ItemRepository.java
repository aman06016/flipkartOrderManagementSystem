package org.aman.repository;

import org.aman.exceptions.InvalidItemException;
import org.aman.model.Item;

import java.util.HashMap;
import java.util.Map;

public class ItemRepository {

    private final Map<String,Item> mapOfIdToItem;

    public ItemRepository(){
        mapOfIdToItem = new HashMap<>();
    }

    public Item findById(String  id){
        Item item =  mapOfIdToItem.get(id);
        if(item == null) {
            throw new InvalidItemException("invalid itemId");
        }
        return item;
    }
    public Item save(Item item){
        mapOfIdToItem.put(item.getId(),item);
        return item;
    }


}
