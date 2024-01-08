package com.charlesedu.saleapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.charlesedu.saleapi.models.ProductModel;

@Repository
public interface IProductRepository extends JpaRepository<ProductModel, Long> {

}
