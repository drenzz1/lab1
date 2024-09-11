package org.foo.services;

import org.foo.dto.InvoiceProductDto;

import java.util.List;

public interface ReportingService {
  List<InvoiceProductDto> getAllInvoiceProducts();

}
