package org.foo.dto;

import org.foo.enums.InvoiceStatus;
import org.foo.enums.InvoiceType;

import java.math.BigDecimal;
import java.time.LocalDate;

public record InvoiceDto(Long id,
                         String invoiceNr,
                         InvoiceStatus invoiceStatus,
                         InvoiceType invoiceType,
                         LocalDate invoiceDate,
                         CompanyDto companyDto,
                         ClientVendorDto clientVendorDto,
                         BigDecimal price,
                         BigDecimal tax,
                         BigDecimal total
) {
}
