package com.charlesedu.saleapi.dto;

import java.math.BigDecimal;

public class ProductDTO {
    private String name;
    private Integer quantity;
    private BigDecimal price;

    public ProductDTO() {
    }

    public ProductDTO(String name, Integer quantity, BigDecimal price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
