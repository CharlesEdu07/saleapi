package com.charlesedu.saleapi.dto;

public class SaleDTO {
    private CustomerDTO customer;
    private ProductDTO product;

    public SaleDTO() {
    }

    public SaleDTO(CustomerDTO customer, ProductDTO product) {
        this.customer = customer;
        this.product = product;
    }

    public CustomerDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }
}
