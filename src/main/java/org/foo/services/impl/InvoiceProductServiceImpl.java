package org.foo.services.impl;

import org.foo.dto.InvoiceDto;
import org.foo.dto.InvoiceProductDto;
import org.foo.dto.ProductDto;
import org.foo.enums.InvoiceStatus;
import org.foo.enums.InvoiceType;
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
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
  public InvoiceProductDto findInvoiceProductById(Long id) {
    return invoiceProductDtoMapper.apply(invoiceProductRepository.findById(id).get());
  }

  @Override
  public List<InvoiceProductDto> findInvoiceProductsByInvoiceId(Long id) {
    return invoiceProductRepository.findAllByInvoice_Id(id).stream().map(invoiceProductDtoMapper).peek(this::calculateTotal).toList();
  }

  @Override
  public boolean productHasInvoice(Long productId) {
    return invoiceProductRepository.findByProduct_Id(productId).isPresent();
  }

  @Override
  public List<InvoiceProductDto> findInvoiceProductsForProduct(InvoiceType invoiceType, ProductDto product) {
    Product product1 = productRepository.findById(product.id()).get();
    List<InvoiceProduct> invoiceProducts = invoiceProductRepository.findApprovedInvoiceProductsForProduct(invoiceType,product1);

      return invoiceProducts.stream().map(invoiceProductDtoMapper).toList();
  }

  @Override
  public void calculateTotal(InvoiceProductDto invoiceProductDto) {
    BigDecimal totalPrice = invoiceProductDto.price()
      .multiply(BigDecimal.valueOf(invoiceProductDto.quantity()));

    BigDecimal totalTax = totalPrice
      .multiply(BigDecimal.valueOf((double) invoiceProductDto.tax() / 100))
      .setScale(2, RoundingMode.HALF_UP);


    BigDecimal sum = (totalPrice.add(totalTax));
  }


  @Override
  public void deleteById(Long invoiceProductId) {
    InvoiceProduct invoiceProduct = invoiceProductRepository.findById(invoiceProductId).get();

    invoiceProduct.setIsDeleted(true);
    invoiceProductRepository.save(invoiceProduct);


  }

  @Override
  public void deleteInvProductsByInvoiceId(Long invoiceId) {

    List<InvoiceProduct> invoiceProducts = invoiceProductRepository.findAllByInvoice_Id(invoiceId);
    invoiceProducts.forEach(invoiceProduct -> invoiceProduct.setIsDeleted(true));

    invoiceProductRepository.saveAll(invoiceProducts);



  }

  @Override
  public void addProductToInvoice(InvoiceProductDto invoiceProductDto, Long invoiceId) {
    Invoice invoice =invoiceRepository.findById(invoiceId).get();
    Product product = productRepository.findById(invoiceProductDto.productDto().id()).get();

    InvoiceProduct invoiceProduct = new InvoiceProduct(invoiceProductDto.quantity(),invoiceProductDto.price(),invoiceProductDto.tax(),BigDecimal.ZERO,invoiceProductDto.remainingQuantity(),invoice,product);

    invoiceProductRepository.save(invoiceProduct);




  }

  @Override
  public BigDecimal getTotalProfitLossForACompany() {
    List<InvoiceDto> invoiceDtoList = invoiceService.getInvoicesForCompany(InvoiceType.SALES)
      .stream().filter(invoiceDto -> invoiceDto.invoiceStatus().equals(InvoiceStatus.APPROVED)).toList();

    BigDecimal totalProfitLoss = BigDecimal.ZERO;
    for (int i = 0;i<invoiceDtoList.size();i++){
      totalProfitLoss = findInvoiceProductsByInvoiceId(invoiceDtoList.get(i).id()).stream().map(InvoiceProductDto::profitLoss).reduce(totalProfitLoss,BigDecimal::add);
    }
    return totalProfitLoss;
  }

  @Override
  public Integer calculateRemainingQuantity(InvoiceProductDto invoiceProductDto) {
    Integer currentQuantity = productService.getProductById(invoiceProductDto.productDto().id()).quantityInStock();
    return currentQuantity - invoiceProductDto.quantity();

  }

  @Override
  public boolean checkRemainingQuantity(InvoiceProductDto invoiceProductDto) {
    return invoiceProductDto.quantity() <= productService.getProductById(invoiceProductDto.productDto().id()).quantityInStock();

  }

  @Override
  public BigDecimal getTotalCostForACompany() {
    List<InvoiceDto> invoiceDtoList = invoiceService.getInvoicesForCompany(InvoiceType.PURCHASE)
      .stream().filter(invoiceDto -> invoiceDto.invoiceStatus().equals(InvoiceStatus.APPROVED)).toList();

    BigDecimal totalCost = BigDecimal.ZERO;

    for (int i = 0 ; i<invoiceDtoList.size();i++){
      totalCost = findInvoiceProductsByInvoiceId(invoiceDtoList.get(i).id())
        .stream().map(InvoiceProductDto::total).reduce(totalCost,BigDecimal::add);
    }

    return totalCost;

  }

  @Override
  public BigDecimal getTotalSalesForACompany() {
    List<InvoiceDto> invoiceDtoList = invoiceService.getInvoicesForCompany(InvoiceType.SALES)
      .stream().filter(invoiceDto -> invoiceDto.invoiceStatus().equals(InvoiceStatus.APPROVED))
      .toList();

    BigDecimal totalSales = BigDecimal.ZERO;

    for (int i = 0 ; i<invoiceDtoList.size() ; i++){
      totalSales = findInvoiceProductsByInvoiceId(invoiceDtoList.get(i).id())
        .stream().map(InvoiceProductDto::total).reduce(totalSales,BigDecimal::add);
    }

    return totalSales;
  }

  @Override
  public void updateInvoiceProduct( InvoiceProductDto invoiceProductDto) {

    invoiceProductRepository.findById(invoiceProductDto.id()).ifPresent(invoiceProduct -> {
      invoiceProduct.setRemainingQuantity(invoiceProductDto.remainingQuantity());
      invoiceProduct.setProfitLoss(invoiceProductDto.profitLoss());

      invoiceProductRepository.save(invoiceProduct);
    });

  }

  @Override
  public void calculateProfitLoss(InvoiceDto invoiceDto) {
    List<InvoiceProductDto> invoiceProductDtoList = findInvoiceProductsByInvoiceId(invoiceDto.id());

    Map<ProductDto, List<InvoiceProductDto>> purchaseProductsMap = new HashMap<>();
    Map<ProductDto, List<InvoiceProductDto>> salesProductsMap = new HashMap<>();

    invoiceProductDtoList.forEach(invoiceProductDto -> {
      ProductDto productDto = invoiceProductDto.productDto();

      //find all approved purchase invoices for the product and order by id
      List<InvoiceProductDto> purchaseInvoiceProductList = findInvoiceProductsForProduct(InvoiceType.PURCHASE, productDto);

      //find all approved sales invoices for the product and order by id
      List<InvoiceProductDto> salesInvoiceProductList = findInvoiceProductsForProduct(InvoiceType.SALES, productDto);

      purchaseProductsMap.put(productDto, purchaseInvoiceProductList);
      salesProductsMap.put(productDto, salesInvoiceProductList);
    });

    List<BigDecimal> purchasePrices = new ArrayList<>();
    List<Integer> purchaseQuantities = new ArrayList<>();

    purchaseProductsMap.forEach((productDto, invoiceProductDtos) -> {
      invoiceProductDtos.forEach(invoiceProductDto -> {
        BigDecimal purchasePriceWithTax = invoiceProductDto.price().add(invoiceProductDto.price().multiply(BigDecimal.valueOf(invoiceProductDto.tax()).divide(BigDecimal.valueOf(100))));
        purchasePrices.add(purchasePriceWithTax);
        purchaseQuantities.add(invoiceProductDto.quantity());
      });
    });

    List<Integer> salesQuantities = new ArrayList<>();

    salesProductsMap.forEach((productDto, invoiceProductDtos) -> {
      invoiceProductDtos.forEach(invoiceProductDto -> {
        salesQuantities.add(invoiceProductDto.quantity());
      });
    });

    int totalSalesQuantities = salesQuantities.stream()
      .mapToInt(Integer::intValue)
      .sum();

    invoiceProductDtoList.forEach(invoiceProductDto -> {
      int totalQuantities = 0;
      int currentPriceIndex = 0;
      int quantitiesToSale = invoiceProductDto.quantity();

      for (int i = 0; i < purchaseQuantities.size(); i++) {
        totalQuantities = totalQuantities + purchaseQuantities.get(i);
        if (totalSalesQuantities < totalQuantities) {
          currentPriceIndex = i;

          break;
        }
      }

      BigDecimal profitLoss = BigDecimal.ZERO;
      int sellingQuantities = totalSalesQuantities + 1;
      BigDecimal salesPriceWithTax = invoiceProductDto.price().add(invoiceProductDto.price().multiply(BigDecimal.valueOf(invoiceProductDto.tax()).divide(BigDecimal.valueOf(100))));

      for (int i = 0; i < quantitiesToSale; i++) {
        if (sellingQuantities > totalQuantities) {
          totalQuantities = totalQuantities + purchaseQuantities.get(currentPriceIndex + 1);
          currentPriceIndex++;
        }

        profitLoss = profitLoss.add(salesPriceWithTax.subtract(purchasePrices.get(currentPriceIndex)));
        sellingQuantities++;
      }

      InvoiceProductDto productDto2  = new InvoiceProductDto(invoiceProductDto.id(),invoiceProductDto.quantity(),invoiceProductDto.price(),invoiceProductDto.tax(),invoiceProductDto.total(),profitLoss,invoiceProductDto.remainingQuantity(),invoiceProductDto.invoiceDto(),invoiceProductDto.productDto());
      updateInvoiceProduct(productDto2);
    });

  }
}
