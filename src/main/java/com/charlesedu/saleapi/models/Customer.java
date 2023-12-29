package com.charlesedu.saleapi.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "TB_CUSTOMER")
public class Customer extends AbstractEntity<Long> {
    
}
