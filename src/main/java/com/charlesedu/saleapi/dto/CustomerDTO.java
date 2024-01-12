package com.charlesedu.saleapi.dto;

import jakarta.validation.constraints.NotEmpty;

public class CustomerDTO {

    @NotEmpty(message = "Telephone is mandatory")
    private String telephone;

    public CustomerDTO() {
    }

    public CustomerDTO(String name, String telephone) {
        this.telephone = telephone;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
