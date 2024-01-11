package com.charlesedu.saleapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.charlesedu.saleapi.models.CustomerModel;

@Repository
public interface ICustomerRepository extends JpaRepository<CustomerModel, Long> {
    CustomerModel findByTelephone(String telephone);
}
