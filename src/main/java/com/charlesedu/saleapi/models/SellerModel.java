package com.charlesedu.saleapi.models;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.format.annotation.NumberFormat.Style;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

@SuppressWarnings("serial")
@Entity
@Table(name = "TB_SELLER")
public class SellerModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 3, max = 255)
    @Column(nullable = false, unique = true)
    private String name;

    @NotNull
    @NumberFormat(style = Style.CURRENCY, pattern = "#,##0.00")
    @Column(nullable = false, columnDefinition = "DECIMAL(7,2) DEFAULT 0.00")
    private BigDecimal salary;

    @NotNull
    @PastOrPresent(message = "Admission date must be in the past or present")
    @DateTimeFormat(iso = ISO.DATE)
    @Column(name = "admission_date", nullable = false, columnDefinition = "DATE")
    private LocalDate admissionDate;

    @DateTimeFormat(iso = ISO.DATE)
    @Column(name = "exit_date", columnDefinition = "DATE")
    private LocalDate exitDate;
}
