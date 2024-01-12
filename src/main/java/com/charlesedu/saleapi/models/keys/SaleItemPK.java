package com.charlesedu.saleapi.models.keys;

import java.io.Serializable;

import com.charlesedu.saleapi.models.ProductModel;
import com.charlesedu.saleapi.models.SaleModel;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Embeddable
public class SaleItemPK implements Serializable {
    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "sale_id")
    private SaleModel sale;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductModel product;

    @JsonIgnore
    public SaleModel getSale() {
        return sale;
    }

    public void setSale(SaleModel sale) {
        this.sale = sale;
    }

    public ProductModel getProduct() {
        return product;
    }

    public void setProduct(ProductModel product) {
        this.product = product;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((sale == null) ? 0 : sale.hashCode());
        result = prime * result + ((product == null) ? 0 : product.hashCode());
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
        SaleItemPK other = (SaleItemPK) obj;
        if (sale == null) {
            if (other.sale != null)
                return false;
        } else if (!sale.equals(other.sale))
            return false;
        if (product == null) {
            if (other.product != null)
                return false;
        } else if (!product.equals(other.product))
            return false;
        return true;
    }
}
