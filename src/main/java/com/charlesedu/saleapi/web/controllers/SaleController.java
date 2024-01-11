package com.charlesedu.saleapi.web.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sales")
public class SaleController {

    // @Autowired
    // private SaleService saleService;

    // @PostMapping("/save")
    // public ResponseEntity<?> save(@RequestBody SaleDTO saleDTO, BindingResult result) {
    //     if (result.hasErrors()) {
    //         return ResponseEntity.status(400).body(result.getAllErrors());
    //     }

    //     SaleModel saleModel = saleService.save(saleDTO);

    //     URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(saleModel.getId())
    //             .toUri();

    //     return ResponseEntity.created(uri).body(saleModel);
    // }

    // @GetMapping("/list")
    // public ResponseEntity<List<SaleModel>> findAll() {
    // List<SaleModel> sales = saleService.findAll();

    // return ResponseEntity.ok().body(sales);
    // }

    // @GetMapping("/list/{id}")
    // public ResponseEntity<SaleModel> findById(@PathVariable("id") Long id) {
    // SaleModel sale = saleService.findById(id);

    // return ResponseEntity.ok().body(sale);
    // }

    // @PutMapping("/update/{id}")
    // public ResponseEntity<?> update(@PathVariable("id") Long id, @Valid
    // @RequestBody SaleModel saleModel,
    // BindingResult result) {

    // if (result.hasErrors()) {
    // return
    // ResponseEntity.status(400).body(result.getAllErrors().get(0).getDefaultMessage());
    // }

    // SaleModel updatedSale = saleService.update(id, saleModel);

    // return ResponseEntity.ok().body(updatedSale);
    // }

    // @DeleteMapping("/delete/{id}")
    // public ResponseEntity<?> delete(@PathVariable("id") Long id) {
    // saleService.deleteById(id);

    // return ResponseEntity.ok().body("Sale deleted");
    // }
}
