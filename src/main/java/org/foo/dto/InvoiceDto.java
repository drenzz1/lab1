package org.foo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.foo.enums.InvoiceStatus;
import org.foo.enums.InvoiceType;
import org.foo.models.InvoiceProduct;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceDto {

  private Long id;

  private String invoiceNo;

  private InvoiceStatus invoiceStatus;

  private InvoiceType invoiceType;

  private LocalDate date;

  private CompanyDto company;

  private ClientVendorDto clientVendor;

  private BigDecimal price;
  private BigDecimal tax;
  private BigDecimal total;

  private List<InvoiceProduct> invoiceProducts;

}
