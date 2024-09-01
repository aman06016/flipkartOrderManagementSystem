package org.aman.service;

import org.aman.component.InventoryType;
import org.aman.model.Inventory;
import org.aman.model.Seller;
import org.aman.repository.InMemoryRepository;
import org.aman.repository.InventoryRepository;
import org.aman.repository.SellerRepository;

public class SellerService {

    private final SellerRepository sellerRepository = InMemoryRepository.sellerRepository;
    private final InventoryRepository inventoryRepository = InMemoryRepository.inventoryRepository;

    public Seller registerSeller(String id, String name, String email, InventoryType inventoryType){
        Seller seller = new Seller();

        seller.setId(id);
        seller.setName(name);
        seller.setEmail(email);

        System.out.printf("%s seller has been registered %s \n" , name, seller);

        Inventory inventory = inventoryRepository.createInventoryForTheSeller(seller,inventoryType);
        seller.setInventoryId(inventory.getId());

        return sellerRepository.save(seller);
    }




}
