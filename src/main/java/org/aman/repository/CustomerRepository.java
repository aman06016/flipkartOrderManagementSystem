package org.aman.repository;

import org.aman.model.Customer;

import java.util.HashMap;
import java.util.Map;

public class CustomerRepository {

    private final Map<String , Customer> mapOfIdToCustomer;

    public CustomerRepository(){
        mapOfIdToCustomer = new HashMap<>();
    }

    public Customer findById(String id){
        return mapOfIdToCustomer.get(id);
    }
    public Customer save(Customer customer){
        mapOfIdToCustomer.put(customer.getId(),customer);
        return customer;
    }

}
