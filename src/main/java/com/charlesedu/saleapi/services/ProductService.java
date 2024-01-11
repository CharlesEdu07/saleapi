package com.charlesedu.saleapi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.charlesedu.saleapi.dto.ProductDTO;
import com.charlesedu.saleapi.models.ProductModel;
import com.charlesedu.saleapi.repositories.IProductRepository;
import com.charlesedu.saleapi.services.exceptions.DatabaseException;
import com.charlesedu.saleapi.services.exceptions.ResourceNotFoundException;

@Service
public class ProductService {

    @Autowired
    private IProductRepository repository;

    public ProductModel save(ProductModel product) {
        if (product.getName() != null) {
            ProductModel productExists = repository.findByName(product.getName());

            if (productExists != null) {
                throw new DatabaseException("Product already exists");
            }
        }

        return repository.save(product);
    }

    public List<ProductModel> findAll() {
        return repository.findAll();
    }

    public ProductModel findById(Long id) {
        Optional<ProductModel> product = repository.findById(id);

        return product.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public ProductModel findByName(String name) {
        ProductModel product = repository.findByName(name);

        return product;
    }

    public ProductModel update(Long id, ProductModel productModel) {
        Optional<ProductModel> product = repository.findById(id);

        if (product.isPresent()) {
            ProductModel updatedProduct = product.get();

            updateData(updatedProduct, productModel);

            repository.save(updatedProduct);

            return updatedProduct;
        } else {
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(ProductModel entity, ProductModel obj) {
        entity.setName(obj.getName());
        entity.setPrice(obj.getPrice());
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

    public ProductModel fromDTO(ProductDTO productDTO) {
        if (productDTO.getName() != null) {
            ProductModel productExists = repository.findByName(productDTO.getName());

            if (productExists != null) {
                return productExists;
            } else {
                throw new DatabaseException("Product not found");
            }
        } else {
            throw new DatabaseException("Product name is required");
        }
    }
}
