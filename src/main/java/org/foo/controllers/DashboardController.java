package org.foo.controllers;

import org.foo.dto.InvoiceDto;
import org.foo.services.DashboardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
  private final DashboardService dashboardService;

  public DashboardController(DashboardService dashboardService) {
    this.dashboardService = dashboardService;
  }

  @GetMapping("/last3invoices")
  public List<InvoiceDto> getLast3Invoices(){
    return dashboardService.getLast3Invoices();
  }
}
