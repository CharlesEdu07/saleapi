package com.charlesedu.saleapi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.charlesedu.saleapi.models.SaleModel;
import com.charlesedu.saleapi.repositories.ISaleRepository;
import com.charlesedu.saleapi.services.exceptions.DatabaseException;
import com.charlesedu.saleapi.services.exceptions.ResourceNotFoundException;

@Service
public class SaleService {

    @Autowired
    private ISaleRepository repository;

    public SaleModel save(SaleModel sale) {
        return repository.save(sale);
    }

    public List<SaleModel> findAll() {
        return repository.findAll();
    }

    public SaleModel findById(Long id) {
        Optional<SaleModel> sale = repository.findById(id);

        return sale.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public SaleModel update(Long id, SaleModel saleModel) {
        Optional<SaleModel> sale = repository.findById(id);

        if (sale.isPresent()) {
            SaleModel updatedSale = sale.get();

            updateData(updatedSale, saleModel);

            repository.save(updatedSale);

            return updatedSale;
        } else {
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(SaleModel entity, SaleModel obj) {
        entity.setCustomer(obj.getCustomer());
        entity.setMoment(obj.getMoment());
    }

    public void deleteById(Long id) {
        try {
            if (repository.existsById(id)) {
                repository.deleteById(id);
            } else {
                throw new ResourceNotFoundException(id);
            }
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }
}
