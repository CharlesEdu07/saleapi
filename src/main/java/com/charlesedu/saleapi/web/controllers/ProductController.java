package com.charlesedu.saleapi.web.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.charlesedu.saleapi.models.ProductModel;
import com.charlesedu.saleapi.services.ProductService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/save")
    public ResponseEntity<?> save(@Valid @RequestBody ProductModel productModel, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(400).body(result.getAllErrors().get(0).getDefaultMessage());
        }

        productModel = productService.save(productModel);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(productModel.getId())
                .toUri();

        return ResponseEntity.created(uri).body(productModel);
    }

    @GetMapping("/list")
    public ResponseEntity<List<ProductModel>> findAll() {
        List<ProductModel> products = productService.findAll();

        return ResponseEntity.ok().body(products);
    }

    @GetMapping("/list/{id}")
    public ResponseEntity<ProductModel> findById(@PathVariable("id") Long id) {
        ProductModel product = productService.findById(id);

        return ResponseEntity.ok().body(product);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @Valid @RequestBody ProductModel productModel,
            BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.status(400).body(result.getAllErrors().get(0).getDefaultMessage());
        }

        ProductModel updatedProduct = productService.update(id, productModel);

        return ResponseEntity.ok().body(updatedProduct);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        productService.deleteById(id);

        return ResponseEntity.ok().body("Customer deleted");
    }
}
