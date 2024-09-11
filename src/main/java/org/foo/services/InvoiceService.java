package org.foo.services;

import org.foo.dto.InvoiceDto;
import org.foo.enums.InvoiceType;

import java.util.List;

public interface InvoiceService {
  List<InvoiceDto> getInvoicesForCompany(InvoiceType invoiceType);

  InvoiceDto findInvoiceById(Long id);

  void deleteById(Long id);

  void approveById(Long id);

  InvoiceDto createNewInvoice(InvoiceType invoiceType);
  int getNumberOfInvoicesForCompany(InvoiceType invoiceType);

  InvoiceDto save(InvoiceDto invoiceDto, InvoiceType invoiceType);

  void updateInvoice(InvoiceDto invoiceDto, Long invoiceId);

  void updateProductQuantityInStock(InvoiceDto invoice);

  List<InvoiceDto> getLast3InvoicesForCompany();
}
