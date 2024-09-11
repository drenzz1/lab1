package org.foo.controllers;


import org.foo.dto.InvoiceProductDto;
import org.foo.services.ReportingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportingController {

  private final ReportingService reportingService;

  public ReportingController(ReportingService reportingService) {
    this.reportingService = reportingService;
  }

  @GetMapping("/stockData")
  public List<InvoiceProductDto> getStockData(){
    return reportingService.getAllInvoiceProducts();
  }
}
