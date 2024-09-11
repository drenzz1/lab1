package org.foo.services;

import org.foo.dto.InvoiceDto;
import org.foo.dto.InvoiceProductDto;
import org.foo.dto.ProductDto;
import org.foo.enums.InvoiceType;

import java.math.BigDecimal;
import java.util.List;

public interface InvoiceProductService {
  InvoiceProductDto findInvoiceProductById(Long id);
  List<InvoiceProductDto> findInvoiceProductsByInvoiceId(Long id);

  boolean productHasInvoice(Long productId);

  List<InvoiceProductDto> findInvoiceProductsForProduct(InvoiceType invoiceType, ProductDto product);
  void calculateTotal(InvoiceProductDto invoiceProductDto);



  void deleteById(Long invoiceProductId);

  void deleteInvProductsByInvoiceId(Long invoiceId);

  void addProductToInvoice(InvoiceProductDto invoiceProductDto, Long invoiceId);


  BigDecimal getTotalProfitLossForACompany();

  Integer calculateRemainingQuantity(InvoiceProductDto invoiceProductDto);

  boolean checkRemainingQuantity(InvoiceProductDto invoiceProductDto);

  //find total cost for a company
  BigDecimal getTotalCostForACompany();

  BigDecimal getTotalSalesForACompany();

  void updateInvoiceProduct(InvoiceProductDto invoiceProductDto);

  void calculateProfitLoss(InvoiceDto invoiceDto);
}
