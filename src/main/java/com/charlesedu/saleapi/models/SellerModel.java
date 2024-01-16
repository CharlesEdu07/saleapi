package com.charlesedu.saleapi.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.format.annotation.NumberFormat.Style;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "TB_SELLER")
public class SellerModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is mandatory")
    @Size(min = 3, max = 255, message = "Name must be between 3 and 255 characters")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Username is mandatory")
    @Size(min = 3, max = 255, message = "Username must be between 3 and 255 characters")
    @Column(nullable = false, unique = true)
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "Password is mandatory")
    @Column(nullable = false)
    private String password;

    @NotNull
    @NumberFormat(style = Style.CURRENCY, pattern = "#,##0.00")
    @Column(nullable = false, columnDefinition = "DECIMAL(7,2)")
    private BigDecimal salary;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @NotNull
    @PastOrPresent(message = "Admission date must be in the past or present")
    @DateTimeFormat(iso = ISO.DATE)
    @Column(name = "admission_date", nullable = false, columnDefinition = "DATE")
    private LocalDate admissionDate;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @DateTimeFormat(iso = ISO.DATE)
    @Column(name = "exit_date", columnDefinition = "DATE")
    private LocalDate exitDate;

    @JsonIgnore
    @OneToMany(mappedBy = "seller")
    private List<SaleModel> sales = new ArrayList<>();

    public SellerModel() {
    }

    public SellerModel(Long id, String name, String username, String password, BigDecimal salary,
            LocalDate admissionDate, LocalDate exitDate) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.salary = salary;
        this.admissionDate = admissionDate;
        this.exitDate = exitDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public LocalDate getAdmissionDate() {
        return admissionDate;
    }

    public void setAdmissionDate(LocalDate admissionDate) {
        this.admissionDate = admissionDate;
    }

    public LocalDate getExitDate() {
        return exitDate;
    }

    public void setExitDate(LocalDate exitDate) {
        this.exitDate = exitDate;
    }

    public List<SaleModel> getSales() {
        return sales;
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
        SellerModel other = (SellerModel) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
