package com.charlesedu.saleapi.web.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.charlesedu.saleapi.dto.SaleDTO;
import com.charlesedu.saleapi.models.SaleModel;
import com.charlesedu.saleapi.services.SaleService;

@RestController
@RequestMapping("/sales")
public class SaleController {

    @Autowired
    private SaleService saleService;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody SaleDTO saleDTO, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(400).body(result.getAllErrors());
        }

        SaleModel saleModel = saleService.fromDTO(saleDTO);

        saleModel = saleService.save(saleModel);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(12)
                .toUri();

        return ResponseEntity.created(uri).body(saleModel);
    }
}
