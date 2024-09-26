package org.foo.services;

import org.foo.dto.ProductDto;

import java.util.List;

public interface ProductService {
  void saveProduct(ProductDto productDto);

  List<ProductDto> getAllProducts();

  List<ProductDto> getProductsByCompanyId(Long companyId);

  List<ProductDto> getProductsInStockForCompany();

  ProductDto getProductById(Long id);

  boolean productNameIsExist(ProductDto productDto);

  void updateProduct(Long id , ProductDto productDto);

  void deleteProductById(Long id);

  ProductDto getProductByName(String name);
}
