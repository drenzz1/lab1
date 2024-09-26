package org.foo.controllers;

import org.foo.dto.ClientVendorDto;
import org.foo.dto.InvoiceDto;
import org.foo.enums.ClientVendorType;
import org.foo.enums.InvoiceType;
import org.foo.models.ClientVendor;
import org.foo.services.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/salesInvoices")
public class SalesInvoiceController {
  private final InvoiceService invoiceService;
  private final InvoiceProductService invoiceProductService;
  private final ClientVendorService clientVendorService;
  private final ProductService productService;
  private final CompanyService companyService;
  private final SecurityService securityService;

  public SalesInvoiceController(InvoiceService invoiceService, InvoiceProductService invoiceProductService, ClientVendorService clientVendorService, ProductService productService, CompanyService companyService, SecurityService securityService) {
    this.invoiceService = invoiceService;
    this.invoiceProductService = invoiceProductService;
    this.clientVendorService = clientVendorService;
    this.productService = productService;
    this.companyService = companyService;
    this.securityService = securityService;
  }

  @GetMapping("/create")
  public ResponseEntity<String> createNewInvoice(){

    return ResponseEntity.status(HttpStatus.OK).build();

  }
  @GetMapping("/listVendors")
  public List<ClientVendorDto> listClientVendorForCompany(){
    return clientVendorService.listClientVendorsForCompany(ClientVendorType.CLIENT);
  }

  @PostMapping("/create")
  public ResponseEntity<String> createSalesInvoice(@RequestBody InvoiceDto invoiceDto){
    invoiceService.save(invoiceDto,InvoiceType.SALES);
    return ResponseEntity.status(HttpStatus.OK).build();

  }
}
