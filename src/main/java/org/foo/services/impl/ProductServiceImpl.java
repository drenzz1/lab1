package org.foo.services.impl;

import org.foo.dto.ProductDto;
import org.foo.mapper.ProductDtoMapper;
import org.foo.models.Category;
import org.foo.models.Product;
import org.foo.repository.CategoryRepository;
import org.foo.repository.ProductRepository;
import org.foo.services.CompanyService;
import org.foo.services.InvoiceProductService;
import org.foo.services.ProductService;
import org.foo.services.SecurityService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService  {

  private final ProductRepository productRepository;
  private final ProductDtoMapper productDtoMapper;
  private final CompanyService companyService;
  private final InvoiceProductService invoiceProductService;
  private final SecurityService securityService;
  private final CategoryRepository categoryRepository;


  public ProductServiceImpl(ProductRepository productRepository, ProductDtoMapper productDtoMapper, CompanyService companyService, @Lazy InvoiceProductService invoiceProductService, SecurityService securityService, CategoryRepository categoryRepository) {
    this.productRepository = productRepository;
    this.productDtoMapper = productDtoMapper;
    this.companyService = companyService;
    this.invoiceProductService = invoiceProductService;
    this.securityService = securityService;
    this.categoryRepository = categoryRepository;
  }

  @Override
  public void saveProduct(ProductDto productDto) {
    Category category = categoryRepository.findById(productDto.categoryDto().id()).get();
    Product product = new Product(productDto.name(),productDto.quantityInStock(),productDto.lowLimitAlert(),productDto.productUnit(),category);

    productRepository.save(product);

  }

  @Override
  public List<ProductDto> getAllProducts() {
    return productRepository.findAll()
      .stream()
      .map(productDtoMapper)
      .toList();
  }



  @Override
  public List<ProductDto> getProductsByCompanyId(Long companyId) {
    return productRepository.findProductsByCategory_CompanyId(companyId)
      .stream()
      .map(productDtoMapper)
      .toList();
  }

  @Override
  public List<ProductDto> getProductsInStockForCompany() {
    Long companyId = companyService.getCompanyDtoByLoggedInUser().id();
    return getAllProducts().stream()
      .filter(productDto -> productDto.categoryDto().companyDto().id().equals(companyId))
      .filter(productDto -> productDto.quantityInStock()>0)
      .toList();
  }

  @Override
  public ProductDto getProductById(Long id) {
    return productDtoMapper.apply(productRepository.findById(id).get());
  }

  @Override
  public boolean productNameIsExist(ProductDto productDto) {
    return true;
  }

  @Override
  public void updateProduct(Long id , ProductDto productDto) {

    Category category = categoryRepository.findById(id).get();

    productRepository.findById(productDto.id()).ifPresent(product -> {
      product.setProductUnit(productDto.productUnit());
      product.setName(productDto.name());
      product.setCategory(category);
      product.setLowLimitAlert(productDto.lowLimitAlert());
      product.setQuantityInStock(productDto.quantityInStock());

      productRepository.save(product);
    });

  }

  @Override
  public void deleteProductById(Long id) {
    if ( getProductById(id).quantityInStock()==0){
      Product product = productRepository.findById(id).get();
      product.setIsDeleted(true);
      productRepository.save(product);
    }
  }

  @Override
  public ProductDto getProductByName(String name) {
    return productDtoMapper.apply(productRepository.findByName(name));
  }
}
