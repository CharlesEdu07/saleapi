package com.charlesedu.saleapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.charlesedu.saleapi.models.SellerModel;

@Repository
public interface ISellerRepository extends JpaRepository<SellerModel, Long> {
    
}
