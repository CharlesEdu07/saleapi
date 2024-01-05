package com.charlesedu.saleapi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.charlesedu.saleapi.models.Customer;
import com.charlesedu.saleapi.repositories.ICustomerRepository;
import com.charlesedu.saleapi.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

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

    public Customer update(Long id, Customer obj) {
        try {
            Customer entity = repository.getReferenceById(id);

            updateData(entity, obj);

            return repository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(Customer entity, Customer obj) {
        entity.setName(obj.getName());
        entity.setTelephone(obj.getTelephone());
        entity.setStatus(obj.getStatus());
    }
}
