package org.foo.services;

import org.foo.dto.InvoiceDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DashboardService {
  String get(String costName);

  List<InvoiceDto> getLast3Invoices();
}
