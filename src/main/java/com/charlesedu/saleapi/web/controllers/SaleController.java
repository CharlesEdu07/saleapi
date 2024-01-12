package com.charlesedu.saleapi.web.controllers;

import java.net.URI;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.charlesedu.saleapi.dto.SaleDTO;
import com.charlesedu.saleapi.models.SaleItemModel;
import com.charlesedu.saleapi.models.SaleModel;
import com.charlesedu.saleapi.services.SaleService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/sales")
public class SaleController {

    @Autowired
    private SaleService saleService;

    @PostMapping("/save")
    public ResponseEntity<?> save(@Valid @RequestBody SaleDTO saleDTO, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(400).body(result.getAllErrors().get(0).getDefaultMessage());
        }

        SaleModel saleModel = saleService.fromCustomerDTO(saleDTO);

        List<SaleItemModel> salesItems = saleService.fromSaleItemDTO(saleDTO, saleModel);

        saleModel = saleService.save(saleModel, salesItems);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(saleModel.getId())
                .toUri();

        return ResponseEntity.created(uri).body(saleModel);
    }

    @GetMapping("/list")
    public ResponseEntity<List<SaleModel>> findAll() {
        List<SaleModel> sales = saleService.findAll();

        return ResponseEntity.ok().body(sales);
    }

    @GetMapping("list/{id}")
    public ResponseEntity<SaleModel> findById(@PathVariable("id") Long id) {
        SaleModel sale = saleService.findById(id);

        return ResponseEntity.ok().body(sale);
    }

    @GetMapping("list/{id}/items")
    public ResponseEntity<Set<SaleItemModel>> listSaleItems(@PathVariable("id") Long id) {
        SaleModel sale = saleService.findById(id);

        Set<SaleItemModel> items = sale.getItems();

        return ResponseEntity.ok().body(items);
    }
}
