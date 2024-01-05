package com.charlesedu.saleapi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.charlesedu.saleapi.models.CustomerModel;
import com.charlesedu.saleapi.repositories.ICustomerRepository;
import com.charlesedu.saleapi.services.exceptions.ResourceNotFoundException;

@Service
public class CustomerService {

    @Autowired
    private ICustomerRepository repository;

    public CustomerModel save(CustomerModel customer) {
        return repository.save(customer);
    }

    public List<CustomerModel> findAll() {
        return repository.findAll();
    }

    public CustomerModel findById(Long id) {
        Optional<CustomerModel> customer = repository.findById(id);

        return customer.orElseThrow(() -> new ResourceNotFoundException(id));
    }
}