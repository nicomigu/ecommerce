package com.nicomigu.product_service.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nicomigu.product_service.dto.ProductCreateDTO;
import com.nicomigu.product_service.dto.ProductDTO;
import com.nicomigu.product_service.dto.ProductUpdateDTO;
import com.nicomigu.product_service.exception.ProductNotFoundException;
import com.nicomigu.product_service.model.Product;
import com.nicomigu.product_service.repository.ProductRepository;
import com.nicomigu.product_service.util.ProductMapper;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductService productService;

    private Product product;
    private ProductDTO productDTO;
    private ProductCreateDTO createDTO;
    private ProductUpdateDTO updateDTO;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setDescription("Test Description");
        product.setPrice(new BigDecimal("99.99"));
        product.setStock(10);

        productDTO = new ProductDTO(1L, "Test Product", "Test Description",
                new BigDecimal("99.99"), 10, "test-url.jpg");

        createDTO = new ProductCreateDTO("Test Product", "Test Description",
                new BigDecimal("99.99"), 10, "test-url.jpg");

        updateDTO = new ProductUpdateDTO("Updated Product", "Updated Description",
                new BigDecimal("149.99"), 20, "updated-url.jpg");
    }

    @Test
    void createProduct_ShouldReturnProductDTO() {
        when(productMapper.toEntity(any(ProductCreateDTO.class))).thenReturn(product);
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(productMapper.toDTO(any(Product.class))).thenReturn(productDTO);

        ProductDTO result = productService.createProduct(createDTO);

        assertNotNull(result);
        assertEquals(productDTO.id(), result.id());
        assertEquals(productDTO.name(), result.name());
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void getProduct_WhenExists_ShouldReturnProductDTO() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productMapper.toDTO(any(Product.class))).thenReturn(productDTO);

        ProductDTO result = productService.getProduct(1L);

        assertNotNull(result);
        assertEquals(productDTO.id(), result.id());
    }

    @Test
    void getProduct_WhenNotExists_ShouldThrowException() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.getProduct(1L));
    }

    @Test
    void updateProduct_WhenExists_ShouldReturnUpdatedProductDTO() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(productMapper.toDTO(any(Product.class))).thenReturn(
                new ProductDTO(1L, "Updated Product", "Updated Description",
                        new BigDecimal("149.99"), 20, "updated-url.jpg"));

        ProductDTO result = productService.updateProduct(1L, updateDTO);

        assertNotNull(result);
        assertEquals("Updated Product", result.name());
        assertEquals(new BigDecimal("149.99"), result.price());
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void deleteProduct_WhenExists_ShouldDeleteSuccessfully() {
        when(productRepository.existsById(1L)).thenReturn(true);

        productService.deleteProduct(1L);

        verify(productRepository).deleteById(1L);
    }

    @Test
    void deleteProduct_WhenNotExists_ShouldThrowException() {
        when(productRepository.existsById(1L)).thenReturn(false);

        assertThrows(ProductNotFoundException.class, () -> productService.deleteProduct(1L));
    }
}