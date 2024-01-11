package com.charlesedu.saleapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.charlesedu.saleapi.models.SaleItemModel;
import com.charlesedu.saleapi.models.keys.SaleItemPK;

@Repository
public interface ISaleItemRepository extends JpaRepository<SaleItemModel, SaleItemPK> {

}
