package org.foo.services;

import org.foo.dto.InvoiceDto;
import org.foo.enums.InvoiceType;

import java.util.List;

public interface InvoiceService {
  InvoiceDto findById(Long id);
  InvoiceDto save(InvoiceDto invoiceDto, InvoiceType purchase);
  InvoiceDto update(InvoiceDto invoiceDto, long id);
  void delete(Long id);

  List<InvoiceDto> findAllInvoicesByCompany();

  void approveInvoice(Long id);

  String assignNextPurchaseInvoiceNumberByCompany(String companyTitle);

  InvoiceDto getNewInvoice(InvoiceType invoiceType, String companyTitle);

  String generateSaleInvoiceNo(String company);
  List<InvoiceDto> findAllSaleInvoicesByCompany(String company);

  List<InvoiceDto> findAllPurchaseInvoicesByCompany(String companyTitle);

  void updatePurchaseInvoiceVendor(InvoiceDto invoiceDto);

  List<InvoiceDto> fetchAllApprovedPurchaseInvoicesOfCompany(String companyTitle);

  List<InvoiceDto> fetchAllApprovedSalesInvoicesOfCompany(String companyTitle);

  List<InvoiceDto> lastThreeApprovedInvoicesOfCompany(String companyTitle);
}
