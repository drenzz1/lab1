package org.foo.controllers;


import org.foo.dto.ClientVendorDto;
import org.foo.dto.InvoiceDto;
import org.foo.dto.InvoiceProductDto;
import org.foo.dto.ProductDto;
import org.foo.enums.ClientVendorType;
import org.foo.enums.InvoiceType;
import org.foo.services.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchase-invoice")
public class PurchaseInvoiceController {

  private final InvoiceService invoiceService;
  private final InvoiceProductService invoiceProductService;
  private final ClientVendorService clientVendorService;
  private final ProductService productService;
  private final CompanyService companyService;
  private final SecurityService securityService;

  public PurchaseInvoiceController(InvoiceService invoiceService, InvoiceProductService invoiceProductService, ClientVendorService clientVendorService, ProductService productService, CompanyService companyService, SecurityService securityService) {
    this.invoiceService = invoiceService;
    this.invoiceProductService = invoiceProductService;
    this.clientVendorService = clientVendorService;
    this.productService = productService;
    this.companyService = companyService;
    this.securityService = securityService;
  }

  @GetMapping("/createNewInvoice")
  public InvoiceDto createToPurchaseInvoice(){

return null;

  }


  @GetMapping("/list")
  public List<InvoiceDto> purchasesInvoiceList(){
    return null;
  }

  @GetMapping("/list/{id}")
  public InvoiceDto findInvoiceById(@PathVariable("id")Long id ){
    return invoiceService.findById(id);
  }

  @GetMapping("/list-invoiceproduct/{id}")
  public List<InvoiceProductDto> listInvoiceProduct(@PathVariable("id")Long id){
    return invoiceProductService.findAllInvoiceProductsByInvoiceId(id);
  }

  @GetMapping("/list-clientvendor")
  public List<ClientVendorDto> listClientVendors(){
    return clientVendorService.listClientVendorsForCompany(ClientVendorType.VENDOR);
  }
  @PostMapping("/create-invoice")
  public ResponseEntity<String> createInvoice(@RequestBody InvoiceDto invoiceDto){
    invoiceService.save(invoiceDto,InvoiceType.PURCHASE);
    return ResponseEntity.status(HttpStatus.OK).build();
  }
  @GetMapping("/invoices-of-company")
  public List<InvoiceDto> getInvoicesForACompany(){
    return invoiceService.findAllInvoicesByCompany();
  }

  @GetMapping("/invoiceProducts/{id}")
  public List<InvoiceProductDto> findInvoiceProductsByInvoiceId(@PathVariable ("id")Long id){
    return invoiceProductService.findAllInvoiceProductsByInvoiceId(id);
  }

  @PostMapping("/addInvoiceProduct/{id}")
  public ResponseEntity<String> addProductToPurchaseInvoice(@PathVariable("id")Long id , @RequestBody InvoiceProductDto invoiceProductDto , @RequestBody ProductDto invoiceDto){
    invoiceProductService.addInvoiceProduct(id,invoiceDto,invoiceProductDto);
    return ResponseEntity.status(HttpStatus.OK).build();

  }

  @PutMapping("/update/{id}")
  public ResponseEntity<String> updatePurchaseInvoice(@PathVariable("id")Long id ,@RequestBody InvoiceDto invoiceDto){
    invoiceService.update(invoiceDto,id);
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<String> deletePurchaseInvoice(@PathVariable("id")Long id){
    invoiceService.delete(id);
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @GetMapping("/approve/{id}")
  public ResponseEntity<String> approvePurchaseInvoice(@PathVariable("id")Long id){
    invoiceService.approveInvoice(id);
    return ResponseEntity.status(HttpStatus.OK).build();
  }
  @DeleteMapping("/removeInvoiceProduct/{invoiceId}/{invoiceProductId}")
  public ResponseEntity<String> removePurchaseInvoiceProduct(@PathVariable("invoiceId") Long id1, @PathVariable("invoiceProductId")Long id2){
    invoiceProductService.removebyInvoiceProductId(id2);
    return ResponseEntity.status(HttpStatus.OK).build();

  }



}
