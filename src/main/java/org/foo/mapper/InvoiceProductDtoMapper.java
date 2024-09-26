package org.foo.mapper;

import org.foo.dto.InvoiceProductDto;
import org.foo.models.InvoiceProduct;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.function.Function;

@Component
public class InvoiceProductDtoMapper implements Function<InvoiceProduct, InvoiceProductDto> {
  private final InvoiceDtoMapper invoiceDtoMapper;
  private final ProductDtoMapper productDtoMapper;

  public InvoiceProductDtoMapper(InvoiceDtoMapper invoiceDtoMapper, ProductDtoMapper productDtoMapper) {
    this.invoiceDtoMapper = invoiceDtoMapper;
    this.productDtoMapper = productDtoMapper;
  }

  @Override
  public InvoiceProductDto apply(InvoiceProduct invoiceProduct) {
    return new InvoiceProductDto(invoiceProduct.getId(),
      invoiceProduct.getQuantity(),
      invoiceProduct.getPrice(),
      invoiceProduct.getTax(),
      BigDecimal.ZERO,
      invoiceProduct.getProfitLoss(),
      invoiceProduct.getRemainingQuantity(),
      invoiceDtoMapper.apply(invoiceProduct.getInvoice()),
      productDtoMapper.apply(invoiceProduct.getProduct()));
  }
}
