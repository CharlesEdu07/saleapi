package com.charlesedu.saleapi.services;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.charlesedu.saleapi.dto.ProductDTO;
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
    private ISaleRepository saleRepository;

    @Autowired
    private ISaleItemRepository saleItemRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProductService productService;

    public SaleModel save(SaleModel saleModel, List<SaleItemModel> salesItems) {
        if (customerService.findById(saleModel.getCustomer().getId()) == null) {
            throw new ResourceNotFoundException(saleModel.getCustomer().getId());
        }

        saleModel = saleRepository.save(saleModel);

        System.out.println(saleModel.getItems());

        for (SaleItemModel item : salesItems) {
            item.getId().setSale(saleModel);

            saleItemRepository.save(item);
        }

        saleModel.getItems().addAll(salesItems);

        return saleModel;
    }

    public List<SaleModel> findAll() {
        return saleRepository.findAll();
    }

    public SaleModel findById(Long id) {
        Optional<SaleModel> sale = saleRepository.findById(id);

        return sale.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public SaleModel fromCustomerDTO(SaleDTO saleDTO) {
        CustomerModel customer = customerService.fromDTO(saleDTO.getCustomer());

        return new SaleModel(null, Instant.now(), customer);
    }

    public List<SaleItemModel> fromSaleItemDTO(SaleDTO saleDTO, SaleModel saleModel) {
        List<SaleItemModel> items = new ArrayList<>();

        for (ProductDTO productDTO : saleDTO.getProducts()) {
            ProductModel productModel = productService.fromDTO(productDTO);

            SaleItemModel item = new SaleItemModel();
            
            item.setSale(saleModel);
            item.getId().setProduct(productModel);
            item.setQuantity(productDTO.getQuantity());
            item.setPrice(productDTO.getPrice());

            items.add(item);
        }

        return items;
    }
}
