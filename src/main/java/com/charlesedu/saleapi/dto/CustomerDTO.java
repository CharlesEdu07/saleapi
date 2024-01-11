package com.charlesedu.saleapi.dto;

public class CustomerDTO {
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
