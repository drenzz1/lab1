package org.foo.controllers;


import org.foo.dto.InvoiceDto;
import org.foo.services.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/purchase-invoice")
public class PurchaseInvoiceController {

  private final InvoiceService invoiceService;
  private final InvoiceProductService invoiceProductService;
  private final ClientVendorService clientVendorService;
  private final ProductService productService;
  private final CompanyService companyService;

  public PurchaseInvoiceController(InvoiceService invoiceService, InvoiceProductService invoiceProductService, ClientVendorService clientVendorService, ProductService productService, CompanyService companyService) {
    this.invoiceService = invoiceService;
    this.invoiceProductService = invoiceProductService;
    this.clientVendorService = clientVendorService;
    this.productService = productService;
    this.companyService = companyService;
  }

  @PostMapping("/create")
  public ResponseEntity<String> createToPurchaseInvoice(@RequestBody InvoiceDto invoiceDto){
    invoiceService.save(invoiceDto,invoiceDto.invoiceType());
    return ResponseEntity.status(HttpStatus.OK).build();
  }
}
