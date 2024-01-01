package com.charlesedu.saleapi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.charlesedu.saleapi.models.Customer;
import com.charlesedu.saleapi.repositories.ICustomerRepository;
import com.charlesedu.saleapi.services.exceptions.ResourceNotFoundException;

@Service
public class CustomerService {

    @Autowired
    private ICustomerRepository repository;

    public Customer save(Customer customer) {
        return repository.save(customer);
    }

    public List<Customer> findAll() {
        return repository.findAll();
    }

    public Customer findById(Long id) {
        Optional<Customer> customer = repository.findById(id);

        return customer.orElseThrow(() -> new ResourceNotFoundException(id));
    }
}
