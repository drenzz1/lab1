package org.foo.services;

import org.foo.dto.InvoiceDto;

import java.util.List;

public interface DashboardService {
  String get(String costName);

  List<InvoiceDto> getLast3Invoices();
}
