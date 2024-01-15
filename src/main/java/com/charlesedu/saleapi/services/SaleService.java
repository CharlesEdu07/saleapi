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
import com.charlesedu.saleapi.models.SellerModel;
import com.charlesedu.saleapi.repositories.ISaleItemRepository;
import com.charlesedu.saleapi.repositories.ISaleRepository;
import com.charlesedu.saleapi.services.exceptions.InactiveModelException;
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

    @Autowired
    private SellerService sellerService;

    public SaleModel save(SaleModel saleModel, List<SaleItemModel> salesItems, Long sellerId) {
        CustomerModel customer = customerService.findById(saleModel.getCustomer().getId());

        SellerModel seller = sellerService.findById(sellerId);

        if (customer == null) {
            throw new ResourceNotFoundException(saleModel.getCustomer().getId());
        }

        if (customer.getStatus() == false) {
            throw new InactiveModelException(saleModel.getCustomer().getId());
        }

        saleModel.setCustomer(customer);
        saleModel.setSeller(seller);

        saleModel = saleRepository.save(saleModel);

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
        return saleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public SaleModel findBySellerId(Long id) {
        SaleModel sale = saleRepository.findBySellerId(id);

        return sale;
    }

    public SaleModel update(Long id, SaleModel saleModel, List<SaleItemModel> salesItems) {
        Optional<SaleModel> sale = saleRepository.findById(id);

        if (sale.isPresent()) {
            SaleModel updatedSale = sale.get();

            updateData(updatedSale, saleModel);

            saleRepository.save(updatedSale);

            return updatedSale;
        } else {
            throw new ResourceNotFoundException(id);
        }
    }

    public SaleModel addSaleItemModel(Long id, SaleItemModel saleItemModel) {
        SaleModel sale = saleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));

        saleItemRepository.save(saleItemModel);

        sale.getItems().add(saleItemModel);

        return sale;
    }

    private void updateData(SaleModel entity, SaleModel obj) {
        entity.setCustomer(obj.getCustomer());
        entity.setMoment(obj.getMoment());
    }

    public SaleModel fromSaleCustomerDTO(SaleDTO saleDTO) {
        CustomerModel customer = customerService.fromDTO(saleDTO.getCustomer());

        return new SaleModel(null, Instant.now(), customer);
    }

    public SaleItemModel fromSaleItemDTO(Long id, ProductDTO productDTO) {
        ProductModel productModel = productService.fromDTO(productDTO);

        SaleItemModel item = new SaleItemModel();

        SaleModel saleModel = findById(id);

        item.setSale(saleModel);
        item.getId().setProduct(productModel);
        item.setQuantity(productDTO.getQuantity());
        item.setPrice(productDTO.getPrice());

        return item;
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
