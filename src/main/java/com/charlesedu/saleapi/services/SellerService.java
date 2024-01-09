package com.charlesedu.saleapi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.charlesedu.saleapi.models.SellerModel;
import com.charlesedu.saleapi.repositories.ISellerRepository;
import com.charlesedu.saleapi.services.exceptions.DatabaseException;
import com.charlesedu.saleapi.services.exceptions.ResourceNotFoundException;

@Service
public class SellerService {
    @Autowired
    private ISellerRepository repository;

    public SellerModel save(SellerModel seller) {
        return repository.save(seller);
    }

    public List<SellerModel> findAll() {
        return repository.findAll();
    }

    public SellerModel findById(Long id) {
        Optional<SellerModel> seller = repository.findById(id);

        return seller.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public SellerModel update(Long id, SellerModel sellerModel) {
        Optional<SellerModel> seller = repository.findById(id);

        if (seller.isPresent()) {
            SellerModel updatedSeller = seller.get();

            updateData(updatedSeller, sellerModel);

            repository.save(updatedSeller);

            return updatedSeller;
        } else {
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(SellerModel entity, SellerModel obj) {
        entity.setName(obj.getName());
        entity.setSalary(obj.getSalary());
        entity.setAdmissionDate(obj.getAdmissionDate());
        entity.setExitDate(obj.getExitDate());
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
