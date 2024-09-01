package org.aman.service;

import org.aman.model.Customer;
import org.aman.repository.CustomerRepository;
import org.aman.repository.InMemoryRepository;

public class CustomerService {

    private final CustomerRepository customerRepository = InMemoryRepository.customerRepository;

    public Customer registerCustomer(String id , String name , String email, String address){


        Customer customer = new Customer();
        customer.setId(id);
        customer.setEmail(email);
        customer.setName(name);
        customer.setAddress(address);
        System.out.printf("%s has been registered \n ",customer);

        return customerRepository.save(customer);
    }
}
