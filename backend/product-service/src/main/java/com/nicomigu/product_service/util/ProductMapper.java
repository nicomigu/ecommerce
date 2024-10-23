package com.nicomigu.product_service.util;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.nicomigu.product_service.dto.ProductCreateDTO;
import com.nicomigu.product_service.dto.ProductDTO;
import com.nicomigu.product_service.model.Product;

import java.util.List;

// Mapper Interface
@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDTO toDTO(Product product);

    List<ProductDTO> toDTOList(List<Product> products);

    @Mapping(target = "id", ignore = true)
    Product toEntity(ProductCreateDTO request);
}
