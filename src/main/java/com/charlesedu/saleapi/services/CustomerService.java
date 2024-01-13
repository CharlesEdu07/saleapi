package com.charlesedu.saleapi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.charlesedu.saleapi.dto.CustomerDTO;
import com.charlesedu.saleapi.models.CustomerModel;
import com.charlesedu.saleapi.repositories.ICustomerRepository;
import com.charlesedu.saleapi.services.exceptions.DatabaseException;
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

    public CustomerModel findByTelephone(String telephone) {
        CustomerModel customer = repository.findByTelephone(telephone);

        return customer;
    }

    public CustomerModel update(Long id, CustomerModel customerModel) {
        Optional<CustomerModel> customer = repository.findById(id);

        if (customer.isPresent()) {
            CustomerModel updatedCustomer = customer.get();

            updateData(updatedCustomer, customerModel);

            repository.save(updatedCustomer);

            return updatedCustomer;
        } else {
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(CustomerModel entity, CustomerModel obj) {
        entity.setName(obj.getName());
        entity.setTelephone(obj.getTelephone());
    }

    public void deleteById(Long id) {
        try {
            if (repository.existsById(id)) {
                repository.deleteById(id);
            } else {
                throw new ResourceNotFoundException(id);
            }
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public CustomerModel fromDTO(CustomerDTO customerDTO) {
        if (customerDTO.getTelephone() != null) {
            CustomerModel customerExists = repository.findByTelephone(customerDTO.getTelephone());

            if (customerExists != null) {
                return customerExists;
            } else {
                throw new DatabaseException("Customer not found");
            }
        } else {
            throw new DatabaseException("Telephone is required");
        }
    }
}
