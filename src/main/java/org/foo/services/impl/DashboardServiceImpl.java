package org.foo.services.impl;

import org.foo.dto.InvoiceDto;
import org.foo.services.DashboardService;
import org.foo.services.InvoiceProductService;
import org.foo.services.InvoiceService;
import org.foo.services.SecurityService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardServiceImpl implements DashboardService {
  private final InvoiceProductService invoiceProductService;
  private final InvoiceService invoiceService;
  private final SecurityService securityService;

  public DashboardServiceImpl(InvoiceProductService invoiceProductService, InvoiceService invoiceService, SecurityService securityService) {
    this.invoiceProductService = invoiceProductService;
    this.invoiceService = invoiceService;
    this.securityService = securityService;
  }

  @Override
  public String get(String costName){

    return null;
  }

  @Override
  public List<InvoiceDto> getLast3Invoices() {
    return null;
  }

}
