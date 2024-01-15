package com.charlesedu.saleapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.charlesedu.saleapi.models.SaleModel;

@Repository
public interface ISaleRepository extends JpaRepository<SaleModel, Long> {
    SaleModel findBySellerId(Long id);
}
