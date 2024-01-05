package com.charlesedu.saleapi.web.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import com.charlesedu.saleapi.models.CustomerModel;
import com.charlesedu.saleapi.services.CustomerService;
import com.charlesedu.saleapi.web.utils.Utils;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/save")
    public ResponseEntity<?> save(@Valid @RequestBody CustomerModel customerModel, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(400).body("Invalid data");
        }

        customerModel = customerService.save(customerModel);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(customerModel.getId())
                .toUri();

        return ResponseEntity.created(uri).body(customerModel);
    }

    @GetMapping("/list")
    public ResponseEntity<List<CustomerModel>> findAll() {
        List<CustomerModel> customers = customerService.findAll();

        return ResponseEntity.ok().body(customers);
    }

    @GetMapping("/list/{id}")
    public ResponseEntity<CustomerModel> findById(@PathVariable("id") Long id) {
        CustomerModel customer = customerService.findById(id);

        return ResponseEntity.ok().body(customer);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @Valid @RequestBody CustomerModel customerModel,
            BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(400).body("Invalid data");
        }

        var customer = customerService.findById(id);

        if (customer == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found");
        }

        Utils.copyNonNullProperties(customerModel, customer);

        var updatedCustomer = customerService.save(customer);

        return ResponseEntity.ok().body(updatedCustomer);
    }

    // @DeleteMapping("/delete/{id}")
    // public ResponseEntity<?> delete(@PathVariable("id") Long id) {
    //     var customer = customerService.findById(id);

    //     if (customer == null) {
    //         return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found");
    //     }

    //     customerService.delete(customer);

    //     return ResponseEntity.ok().body("Customer deleted");
    // }
}