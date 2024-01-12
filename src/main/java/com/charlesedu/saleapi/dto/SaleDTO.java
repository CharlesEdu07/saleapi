package com.charlesedu.saleapi.dto;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.Valid;

public class SaleDTO {

    @Valid
    private CustomerDTO customer;

    @Valid
    private List<ProductDTO> products = new ArrayList<>();

    public SaleDTO() {
    }

    public SaleDTO(CustomerDTO customer) {
        this.customer = customer;
    }

    public CustomerDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    }

    public List<ProductDTO> getProducts() {
        return products;
    }
}
