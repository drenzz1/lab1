package org.foo.mapper;

import org.foo.dto.CompanyDto;
import org.foo.dto.InvoiceDto;
import org.foo.models.Invoice;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.function.Function;

@Component
public class InvoiceDtoMapper implements Function<Invoice, InvoiceDto> {
  private final CompanyDtoMapper companyDtoMapper;
  private final ClientVendorMapper clientVendorMapper;

  public InvoiceDtoMapper(CompanyDtoMapper companyDtoMapper, ClientVendorMapper clientVendorMapper) {
    this.companyDtoMapper = companyDtoMapper;
    this.clientVendorMapper = clientVendorMapper;
  }

  @Override
  public InvoiceDto apply(Invoice invoice) {
    return new InvoiceDto(invoice.getId(),
      invoice.getInvoiceNo(),
      invoice.getInvoiceStatus(),
      invoice.getInvoiceType(),
      invoice.getDate(),
      null,
      null,
      BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO,invoice.getInvoiceProducts()) ;
  }
}
