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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/save")
    public ResponseEntity<Customer> save(@Valid @RequestBody Customer customer, BindingResult result) {
        customer = customerService.save(customer);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(customer.getId())
                .toUri();

        return ResponseEntity.created(uri).body(customer);
    }

    @GetMapping("/")
    public ResponseEntity<List<Customer>> findAll() {
        List<Customer> customers = customerService.findAll();

        return ResponseEntity.ok().body(customers);
    }
}
