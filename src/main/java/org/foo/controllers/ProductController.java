package org.foo.controllers;

import org.foo.dto.ProductDto;
import org.foo.services.CategoryService;
import org.foo.services.InvoiceProductService;
import org.foo.services.ProductService;
import org.foo.services.SecurityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {
  private final ProductService productService;
  private final CategoryService categoryService;
  private final SecurityService securityService;
  private final InvoiceProductService invoiceProductService;

  public ProductController(ProductService productService, CategoryService categoryService, SecurityService securityService, InvoiceProductService invoiceProductService) {
    this.productService = productService;
    this.categoryService = categoryService;
    this.securityService = securityService;
    this.invoiceProductService = invoiceProductService;
  }

  @GetMapping("/list")
  public List<ProductDto> getProducts(){
    return null;
  }

  @PostMapping("/create")
  public ResponseEntity<String> createProduct(@RequestBody ProductDto productDto) {
    if (productService.productNameIsExist(productDto)) {
      throw new NullPointerException("Product exists");
    } else {
      productService.saveProduct(productDto);
    }
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @PutMapping("/edit/{id}")
  public ResponseEntity<String> editProduct(@PathVariable("id") Long id , @RequestBody ProductDto productDto){
    if (productService.productNameIsExist(productDto) && !productService.getProductById(id).name().equals(productDto.name()))
    {
      throw new NullPointerException("Product exists");

    }else {

      productService.updateProduct(id,productDto);
    }
    return ResponseEntity.status(HttpStatus.OK).build();

  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<String> deleteProduct(@PathVariable("id")Long id ){
    if (productService.getProductById(id).quantityInStock()>0){
      productService.deleteProductById(id);
    }
    return ResponseEntity.status(HttpStatus.OK).build();

  }

  @GetMapping
  public List<ProductDto> getProductsInStockForCompany(){
    return productService.getProductsInStockForCompany();
  }
}

