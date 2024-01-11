package com.charlesedu.saleapi.models;

import java.math.BigDecimal;

import com.charlesedu.saleapi.models.keys.SaleItemPK;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "TB_SALE_ITEM")
public class SaleItemModel {

    @EmbeddedId
    private SaleItemPK id = new SaleItemPK();

    private Integer quantity;
    private BigDecimal price;

    public SaleItemModel() {
    }

    public SaleItemModel(SaleModel sale, ProductModel product, Integer quantity, BigDecimal price) {
        this.id.setSale(sale);
        this.id.setProduct(product);
        this.quantity = quantity;
        this.price = price;
    }

    @JsonIgnore
    public SaleModel getSale() {
        return id.getSale();
    }

    public void setSale(SaleModel sale) {
        this.id.setSale(sale);
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

    public BigDecimal getSubTotal() {
        return price.multiply(BigDecimal.valueOf(quantity));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SaleItemModel other = (SaleItemModel) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
