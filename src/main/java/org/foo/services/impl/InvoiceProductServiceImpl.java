package org.foo.services.impl;

import org.foo.dto.InvoiceDto;
import org.foo.dto.InvoiceProductDto;
import org.foo.dto.ProductDto;
import org.foo.exceptions.InvoiceProductNotFoundException;
import org.foo.mapper.InvoiceProductDtoMapper;
import org.foo.models.Invoice;
import org.foo.models.InvoiceProduct;
import org.foo.models.Product;
import org.foo.repository.InvoiceProductRepository;
import org.foo.repository.InvoiceRepository;
import org.foo.repository.ProductRepository;
import org.foo.services.CompanyService;
import org.foo.services.InvoiceProductService;
import org.foo.services.InvoiceService;
import org.foo.services.ProductService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceProductServiceImpl implements InvoiceProductService {

  private final InvoiceProductRepository invoiceProductRepository;
  private final InvoiceProductDtoMapper invoiceProductDtoMapper;
  private final InvoiceService invoiceService;
  private final CompanyService companyService;
  private final ProductService productService;
  private final ProductRepository productRepository;
  private final InvoiceRepository invoiceRepository;

  public InvoiceProductServiceImpl(InvoiceProductRepository invoiceProductRepository, InvoiceProductDtoMapper invoiceProductDtoMapper, @Lazy InvoiceService invoiceService, CompanyService companyService, ProductService productService, ProductRepository productRepository, InvoiceRepository invoiceRepository) {
    this.invoiceProductRepository = invoiceProductRepository;
    this.invoiceProductDtoMapper = invoiceProductDtoMapper;
    this.invoiceService = invoiceService;
    this.companyService = companyService;
    this.productService = productService;
    this.productRepository = productRepository;
    this.invoiceRepository = invoiceRepository;
  }


  @Override
  public InvoiceProductDto findById(Long id) {
    InvoiceProduct invoiceProduct = invoiceProductRepository.findById(id)
      .orElseThrow(()->new InvoiceProductNotFoundException("Invoice product not found "+ id));

    InvoiceProductDto invoiceProductDto = invoiceProductDtoMapper.apply(invoiceProduct);
    invoiceProductDto.setTotal(calcTotal(invoiceProductDto));
    return invoiceProductDto;

  }

  @Override
  public List<InvoiceProductDto> findAllInvoiceProducts(String companyTitle) {
    return invoiceProductRepository.findByCompanyTitle(companyTitle).stream()
      .map(invoiceProduct -> {
        InvoiceProductDto invoiceProductDto = invoiceProductDtoMapper.apply(invoiceProduct);
        invoiceProductDto.setTotal(calcTotal(invoiceProductDto));

        return invoiceProductDto;

      } ).toList();
  }

  @Override
  public List<InvoiceProductDto> findAllInvoiceProductsByInvoiceId(Long invoiceId) {
    return invoiceProductRepository.findAllInvoiceProduct(invoiceId)
      .stream()
      .map(invPrd -> {
          InvoiceProductDto invoiceProduct = invoiceProductDtoMapper.apply(invPrd);
          invoiceProduct.setTotal(calcTotal(invoiceProduct));
          return invoiceProduct;
        }
      )
      .collect(Collectors.toList());
  }

  @Override
  public void removebyInvoiceProductId(Long ipId) {
    InvoiceProduct ip = invoiceProductRepository.findById(ipId).orElseThrow(()->
      new InvoiceProductNotFoundException("Invoice product not found"));
    //  new RuntimeException("Could not find InvoiceProduct for deletion"));

    ip.setIsDeleted(true);

    invoiceProductRepository.save(ip);

  }

  @Override
  public void addInvoiceProduct(Long invoiceId, ProductDto productDto, InvoiceProductDto invoiceProductDto) {
    InvoiceDto invoiceDto = invoiceService.findById(invoiceId);
    ProductDto product = productService.getProductByName(productDto.name());

    invoiceProductDto.setId(null);

    int productInStock = product.quantityInStock();
    int quantity = invoiceProductDto.getQuantity();
    int remainingQuantity = productInStock - quantity;

    if (productInStock==0){
      throw new RuntimeException("Stock is Empty");

    }
    if (remainingQuantity < 0) {
      throw new RuntimeException("there is not enough products, Please choose less quantity");
    }

    ProductDto productDto1 = new ProductDto(productDto.id(),productDto.name(),remainingQuantity,productDto.lowLimitAlert(),productDto.productUnit(),productDto.categoryDto());

    productService.saveProduct(productDto1);

    invoiceProductDto.setProduct(productDto);
    invoiceProductDto.setInvoice(invoiceDto);

    saveInvoiceProduct(invoiceProductDto);




  }

  @Override
  public void saveInvoiceProduct(InvoiceProductDto invoiceProductDto) {
      Invoice invoice =invoiceRepository.findById(invoiceProductDto.getProduct().id()).get();
      Product product = productRepository.findById(invoiceProductDto.getId()).get();
      invoiceProductRepository.save(new InvoiceProduct(invoiceProductDto.getQuantity(),invoiceProductDto.getPrice(),invoiceProductDto.getTax(),invoiceProductDto.getProfitLoss(),invoiceProductDto.getRemainingQuantity(),invoice,product));
  }

  @Override
  public void save(InvoiceProductDto invoiceProductDto) {
    Invoice invoice =invoiceRepository.findById(invoiceProductDto.getProduct().id()).get();
    Product product = productRepository.findById(invoiceProductDto.getId()).get();
    invoiceProductRepository.save(new InvoiceProduct(invoiceProductDto.getQuantity(),invoiceProductDto.getPrice(),invoiceProductDto.getTax(),invoiceProductDto.getProfitLoss(),invoiceProductDto.getRemainingQuantity(),invoice,product));

  }

  @Override
  public BigDecimal calcTax(InvoiceProductDto invoiceProduct) {
    BigDecimal price = calcTotalWithoutTax(invoiceProduct);
    BigDecimal tax = BigDecimal.valueOf(invoiceProduct.getTax()).divide(BigDecimal.valueOf(100));
    return price.multiply(tax);
  }

  @Override
  public BigDecimal calcTotal(InvoiceProductDto invoiceProduct) {
    return calcTotalWithoutTax(invoiceProduct).add(calcTax(invoiceProduct));
  }

  @Override
  public BigDecimal calcTotalWithoutTax(InvoiceProductDto invoiceProduct) {
    int quantity = invoiceProduct.getQuantity();
    BigDecimal price = invoiceProduct.getPrice();
    return price.multiply(BigDecimal.valueOf(quantity));
  }
}
