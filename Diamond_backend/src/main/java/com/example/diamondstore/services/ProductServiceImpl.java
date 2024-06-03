package com.example.diamondstore.services;

import com.example.diamondstore.dto.ProductDTO;
import com.example.diamondstore.entities.Product;
import com.example.diamondstore.repositories.DiamondMountRepository;
import com.example.diamondstore.repositories.ProductRepository;
import com.example.diamondstore.services.interfaces.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private DiamondMountRepository diamondMountRepository;

    @Override
    public List<Product> productList() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(int id) {
        return productRepository.findProductByProductId(id);
    }

    @Override
    public Product createProduct(ProductDTO productDTO) {
        Product saveProduct = productRepository.save(Product.builder()
                        .productName(productDTO.getProductName())
                        .imageUrl(productDTO.getImageUrl())
                        .description(productDTO.getDescription())
                        .mountId(diamondMountRepository.findDiamondMountByMountId(productDTO.getMountId()))
                        .laborFee(productDTO.getLaborFee())
                        .componentsPrice(0)
                        .price(0)
                        .status(productDTO.getStatus())
                        .build());
        return saveProduct;
    }

    @Override
    public Product updateProduct(ProductDTO productDTO, int id) {
        Product saveProduct = productRepository.findProductByProductId(id);
        saveProduct.setProductName(productDTO.getProductName());
        saveProduct.setImageUrl(productDTO.getImageUrl());
        saveProduct.setDescription(productDTO.getDescription());
        saveProduct.setLaborFee(productDTO.getLaborFee());
        saveProduct.setStatus(productDTO.getStatus());
        saveProduct.setMountId(diamondMountRepository.findDiamondMountByMountId(productDTO.getMountId()));

        return productRepository.save(saveProduct);
    }

    @Override
    public List<Product> findProductByProductName(String name) {
        return productRepository.findProductByProductName(name);
    }
}
