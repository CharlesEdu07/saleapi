package com.charlesedu.saleapi.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@SuppressWarnings("serial")
@Entity
@Table(name = "TB_CUSTOMER")
public class Customer extends AbstractEntity<Long> {
    
    @NotBlank
    @Size(min = 3)
    @Column(nullable = false)
    private String name;
    
    @NotNull
    @Size(min = 11, max = 11)
    @Column(nullable = false, unique = true)
    private String telephone;
    
    @NotBlank
    private Boolean status;

    public Customer() {
    }

    public Customer(String name, String telephone, Boolean status) {
        this.name = name;
        this.telephone = telephone;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
