package com.charlesedu.saleapi.services;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.charlesedu.saleapi.dto.SaleDTO;
import com.charlesedu.saleapi.models.CustomerModel;
import com.charlesedu.saleapi.models.ProductModel;
import com.charlesedu.saleapi.models.SaleItemModel;
import com.charlesedu.saleapi.models.SaleModel;
import com.charlesedu.saleapi.repositories.ISaleItemRepository;
import com.charlesedu.saleapi.repositories.ISaleRepository;
import com.charlesedu.saleapi.services.exceptions.ResourceNotFoundException;

@Service
public class SaleService {

    @Autowired
    private ISaleRepository repository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ISaleItemRepository saleItemRepository;

    @Autowired
    private ProductService productService;

    public SaleModel save(SaleModel saleModel, List<SaleItemModel> salesItems) {
        if (customerService.findById(saleModel.getCustomer().getId()) == null) {
            throw new ResourceNotFoundException(saleModel.getCustomer().getId());
        }

        saleModel = repository.save(saleModel);

        for (SaleItemModel item : salesItems) {
            item.getId().setSale(saleModel);
            saleItemRepository.save(item);
        }

        saleModel.setMoment(Instant.now());

        saleModel = repository.save(saleModel);

        return saleModel;
    }

    public List<SaleModel> findAll() {
        return repository.findAll();
    }

    public SaleModel findById(Long id) {
        Optional<SaleModel> sale = repository.findById(id);

        return sale.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public SaleModel fromCustomerDTO(SaleDTO saleDTO) {
        CustomerModel customer = customerService.fromDTO(saleDTO.getCustomer());

        return new SaleModel(null, Instant.now(), customer);
    }

    public List<SaleItemModel> fromSaleItemDTO(SaleDTO saleDTO, SaleModel saleModel) {
        List<ProductModel> products = saleDTO.getProducts().stream().map(item -> {
            return productService.fromDTO(item);
        }).toList();

        List<SaleItemModel> items = products.stream().map(item -> {
            return new SaleItemModel(saleModel, item, 1, item.getPrice());
        }).toList();

        return items;
    }
}
