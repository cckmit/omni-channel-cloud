package com.yonyou.occ.ms.product.service.mapper;

import com.yonyou.occ.ms.product.domain.Product;
import com.yonyou.occ.ms.product.service.dto.ProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity Product and its DTO ProductDTO.
 */
@Mapper(componentModel = "spring", uses = {ProductCategoryMapper.class})
public interface ProductMapper extends EntityMapper<ProductDTO, Product> {
    @Override
    @Mapping(source = "productCategory.id", target = "productCategoryId")
    @Mapping(source = "productCategory.code", target = "productCategoryCode")
    @Mapping(source = "productCategory.name", target = "productCategoryName")
    ProductDTO toDto(Product product);

    @Override
    @Mapping(source = "productCategoryId", target = "productCategory")
    Product toEntity(ProductDTO productDTO);

    default Product fromId(String id) {
        if (id == null) {
            return null;
        }
        Product product = new Product();
        product.setId(id);
        return product;
    }
}
