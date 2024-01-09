package com.charlesedu.saleapi.web.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.charlesedu.saleapi.models.SellerModel;
import com.charlesedu.saleapi.services.SellerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/sellers")
public class SellerController {
    @Autowired
    private SellerService sellerService;

    @PostMapping("/save")
    public ResponseEntity<?> save(@Valid @RequestBody SellerModel sellerModel, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(400).body(result.getAllErrors());
        }

        sellerModel = sellerService.save(sellerModel);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(sellerModel.getId())
                .toUri();

        return ResponseEntity.created(uri).body(sellerModel);
    }

    @GetMapping("/list")
    public ResponseEntity<List<SellerModel>> findAll() {
        List<SellerModel> sellers = sellerService.findAll();

        return ResponseEntity.ok().body(sellers);
    }

    @GetMapping("/list/{id}")
    public ResponseEntity<SellerModel> findById(@PathVariable("id") Long id) {
        SellerModel seller = sellerService.findById(id);

        return ResponseEntity.ok().body(seller);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @Valid @RequestBody SellerModel sellerModel,
            BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.status(400).body(result.getAllErrors().get(0).getDefaultMessage());
        }

        SellerModel updatedSeller = sellerService.update(id, sellerModel);

        return ResponseEntity.ok().body(updatedSeller);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        sellerService.deleteById(id);

        return ResponseEntity.ok().body("Seller deleted");
    }
}
