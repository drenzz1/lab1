package org.foo.mapper;

import org.foo.dto.ProductDto;
import org.foo.models.Product;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ProductDtoMapper implements Function<Product, ProductDto> {
  private final CategoryDtoMapper categoryDtoMapper;

  public ProductDtoMapper(CategoryDtoMapper categoryDtoMapper) {
    this.categoryDtoMapper = categoryDtoMapper;
  }

  @Override
  public ProductDto apply(Product product) {
    return new ProductDto(product.getId(),
      product.getName(),
      product.getQuantityInStock(),
      product.getLowLimitAlert(),
      product.getProductUnit(),
      categoryDtoMapper.apply(product.getCategory()));
  }
}
