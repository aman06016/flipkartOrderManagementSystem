package org.aman.repository;

import org.aman.model.Seller;

import java.util.HashMap;
import java.util.Map;

public class SellerRepository {

    private final Map<String, Seller> mapOfIdToSeller;

    public SellerRepository() {
        this.mapOfIdToSeller = new HashMap<>();
    }

    public Seller findById(String id){
        return mapOfIdToSeller.get(id);
    }
    public Seller save(Seller seller){
        mapOfIdToSeller.put(seller.getId(),seller);
        return seller;
    }

}
