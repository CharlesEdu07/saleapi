package com.charlesedu.saleapi.web.controllers;

import java.net.URI;
import java.util.List;
import java.util.Set;

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

import com.charlesedu.saleapi.dto.ProductDTO;
import com.charlesedu.saleapi.dto.SaleDTO;
import com.charlesedu.saleapi.models.SaleItemModel;
import com.charlesedu.saleapi.models.SaleModel;
import com.charlesedu.saleapi.services.SaleService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/sales")
public class SaleController {

    @Autowired
    private SaleService saleService;

    @PostMapping("/save")
    public ResponseEntity<?> save(@Valid @RequestBody SaleDTO saleDTO, BindingResult result,
            HttpServletRequest request) {
        if (result.hasErrors()) {
            return ResponseEntity.status(400).body(result.getAllErrors().get(0).getDefaultMessage());
        }

        Long sellerId = (Long) request.getAttribute("sellerId");

        SaleModel saleModel = saleService.fromSaleCustomerDTO(saleDTO);

        List<SaleItemModel> salesItems = saleService.fromSaleItemDTO(saleDTO, saleModel);

        saleModel = saleService.save(saleModel, salesItems, sellerId);

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

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @Valid @RequestBody SaleDTO saleDTO,
            BindingResult result, HttpServletRequest request) {
        if (result.hasErrors()) {
            return ResponseEntity.status(400).body(result.getAllErrors().get(0).getDefaultMessage());
        }

        Long sellerId = (Long) request.getAttribute("sellerId");

        SaleModel sellerSale = saleService.findBySellerId(sellerId);

        if (sellerSale == null) {
            return ResponseEntity.status(400).body("Vendedor não possui vendas");
        } else {
            SaleModel saleModel = saleService.fromSaleCustomerDTO(saleDTO);

            List<SaleItemModel> salesItems = saleService.fromSaleItemDTO(saleDTO, saleModel);

            SaleModel updatedSale = saleService.update(id, saleModel, salesItems);

            return ResponseEntity.ok().body(updatedSale);
        }
    }

    @PostMapping("/add-item/{id}")
    public ResponseEntity<?> addItem(@PathVariable("id") Long id, @Valid @RequestBody ProductDTO productDTO,
            BindingResult result, HttpServletRequest request) {
        if (result.hasErrors()) {
            return ResponseEntity.status(400).body(result.getAllErrors().get(0).getDefaultMessage());
        }

        Long sellerId = (Long) request.getAttribute("sellerId");

        SaleModel sellerSale = saleService.findBySellerId(sellerId);

        if (sellerSale == null) {
            return ResponseEntity.status(400).body("Vendedor não possui vendas");
        } else {
            SaleItemModel saleItemModel = saleService.fromSaleItemDTO(id, productDTO);

            SaleModel updatedSale = saleService.addSaleItemModel(id, saleItemModel);

            return ResponseEntity.ok().body(updatedSale);
        }
    }

    @PostMapping("/remove-item/{id}")
    public ResponseEntity<?> removeItem(@PathVariable("id") Long id, @Valid @RequestBody ProductDTO productDTO,
            BindingResult result, HttpServletRequest request) {
        if (result.hasErrors()) {
            return ResponseEntity.status(400).body(result.getAllErrors().get(0).getDefaultMessage());
        }

        Long sellerId = (Long) request.getAttribute("sellerId");

        SaleModel sellerSale = saleService.findBySellerId(sellerId);

        if (sellerSale == null) {
            return ResponseEntity.status(400).body("Vendedor não possui vendas");
        } else {
            SaleItemModel saleItemModel = saleService.fromSaleItemDTO(id, productDTO);

            SaleModel updatedSale = saleService.removeSaleItemModel(id, saleItemModel);

            return ResponseEntity.ok().body(updatedSale);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        saleService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
