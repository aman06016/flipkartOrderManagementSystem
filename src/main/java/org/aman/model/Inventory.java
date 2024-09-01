package org.aman.model;

import org.aman.component.InventoryType;

import java.util.HashMap;
import java.util.Map;

public class Inventory {

    private String id;
    private Map<String,Integer> mapOfItemIdToQuantity= new HashMap<>();
    private String sellerId;
    private InventoryType inventoryType;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public InventoryType getInventoryType() {
        return inventoryType;
    }

    public void setInventoryType(InventoryType inventoryType) {
        this.inventoryType = inventoryType;
    }

    public Map<String, Integer> getMapOfItemIdToQuantity() {
        return mapOfItemIdToQuantity;
    }

    public void setMapOfItemIdToQuantity(Map<String, Integer> mapOfItemIdToQuantity) {
        this.mapOfItemIdToQuantity = mapOfItemIdToQuantity;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "id='" + id + '\'' +
                ", mapOfItemIdToQuantity=" + mapOfItemIdToQuantity +
                ", sellerId='" + sellerId + '\'' +
                ", inventoryType=" + inventoryType +
                '}';
    }
}
