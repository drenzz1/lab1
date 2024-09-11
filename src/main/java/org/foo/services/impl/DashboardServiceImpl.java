package org.foo.services.impl;

import org.foo.dto.InvoiceDto;
import org.foo.services.DashboardService;
import org.foo.services.InvoiceProductService;
import org.foo.services.InvoiceService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardServiceImpl implements DashboardService{
  private final InvoiceProductService invoiceProductService;
  private final InvoiceService invoiceService;

  public DashboardServiceImpl(InvoiceProductService invoiceProductService, InvoiceService invoiceService) {
    this.invoiceProductService = invoiceProductService;
    this.invoiceService = invoiceService;
  }


  @Override
  public String get(String costName){
    switch (costName){
      case "totalCost":
        return String.valueOf(invoiceProductService.getTotalCostForACompany());
      case "totalSales":
        return String.valueOf(invoiceProductService.getTotalSalesForACompany());
      case "profitLoss":
        return String.valueOf(invoiceProductService.getTotalProfitLossForACompany());
    }
    return null;
  }

  @Override
  public List<InvoiceDto> getLast3Invoices() {
    return invoiceService.getLast3InvoicesForCompany();
  }

}
