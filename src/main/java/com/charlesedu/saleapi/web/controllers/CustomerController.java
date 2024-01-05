package com.charlesedu.saleapi.web.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import com.charlesedu.saleapi.models.Customer;
import com.charlesedu.saleapi.services.CustomerService;

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
    public ResponseEntity<?> save(@Valid @RequestBody Customer customer, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(400).body("Invalid data");
        }

        customer = customerService.save(customer);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(customer.getId())
                .toUri();

        return ResponseEntity.created(uri).body(customer);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Customer>> findAll() {
        List<Customer> customers = customerService.findAll();

        return ResponseEntity.ok().body(customers);
    }

    @GetMapping("/list/{id}")
    public ResponseEntity<Customer> findById(@PathVariable("id") Long id) {
        Customer customer = customerService.findById(id);

        return ResponseEntity.ok().body(customer);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @Valid @RequestBody Customer customer,
            BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(400).body("Invalid data");
        }

        var customerExists = customerService.findById(id);

        if (customerExists == null) {
            return ResponseEntity.notFound().build();
        }

        customer = customerService.update(id, customer);

        return ResponseEntity.ok().body(customer);
    }
}
