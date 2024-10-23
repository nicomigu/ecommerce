package com.nicomigu.product_service.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.nicomigu.product_service.dto.ProductCreateDTO;
import com.nicomigu.product_service.dto.ProductDTO;
import com.nicomigu.product_service.dto.ProductUpdateDTO;
import com.nicomigu.product_service.exception.ProductNotFoundException;
import com.nicomigu.product_service.model.Product;
import com.nicomigu.product_service.repository.ProductRepository;
import com.nicomigu.product_service.util.ProductMapper;

import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public List<ProductDTO> getAllProducts() {
        return productMapper.toDTOList(productRepository.findAll());
    }

    public ProductDTO getProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
        return productMapper.toDTO(product);
    }

    public ProductDTO createProduct(ProductCreateDTO createDTO) {
        Product product = productMapper.toEntity(createDTO);
        Product savedProduct = productRepository.save(product);
        return productMapper.toDTO(savedProduct);
    }

    public ProductDTO updateProduct(Long id, ProductUpdateDTO updateDTO) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        // We can add the update mapping in MapStruct if you prefer
        product.setName(updateDTO.name());
        product.setDescription(updateDTO.description());
        product.setPrice(updateDTO.price());
        product.setStock(updateDTO.stock());
        product.setImageUrl(updateDTO.imageUrl());

        Product updatedProduct = productRepository.save(product);
        return productMapper.toDTO(updatedProduct);
    }

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Product not found");
        }
        productRepository.deleteById(id);
    }
}