package com.charlesedu.saleapi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.charlesedu.saleapi.models.Customer;
import com.charlesedu.saleapi.repositories.ICustomerRepository;

@Service
public class CustomerService {
    
    @Autowired
    private ICustomerRepository repository;

    public List<Customer> findAll() {
        return repository.findAll();
    }
    
}
