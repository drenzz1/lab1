package org.foo.services;

import org.foo.dto.InvoiceProductDto;
import org.foo.dto.ProductDto;

import java.math.BigDecimal;
import java.util.List;

public interface InvoiceProductService {
  InvoiceProductDto findById(Long id);
  List<InvoiceProductDto> findAllInvoiceProducts(String companyTitle);
  List<InvoiceProductDto> findAllInvoiceProductsByInvoiceId(Long invoiceId);
  BigDecimal calcTotalWithoutTax(InvoiceProductDto invoiceProduct);
  BigDecimal calcTotal(InvoiceProductDto invoiceProduct);
  BigDecimal calcTax(InvoiceProductDto invoiceProduct);
  void save(InvoiceProductDto invoiceProductDto);
  void saveInvoiceProduct(InvoiceProductDto invoiceProductDto);
  void removebyInvoiceProductId(Long ipId);

  void addInvoiceProduct(Long invoiceId, ProductDto productDto, InvoiceProductDto invoiceProductDto);
}
