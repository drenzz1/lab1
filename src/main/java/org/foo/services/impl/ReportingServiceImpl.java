package org.foo.services.impl;

import org.foo.dto.InvoiceProductDto;
import org.foo.mapper.InvoiceProductDtoMapper;
import org.foo.repository.InvoiceProductRepository;
import org.foo.services.ReportingService;
import org.foo.services.SecurityService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class ReportingServiceImpl implements ReportingService {
  private final SecurityService securityService;
  private final InvoiceProductDtoMapper invoiceDtoMapper;
  private final InvoiceProductRepository invoiceProductRepository;


  public ReportingServiceImpl(SecurityService securityService, InvoiceProductDtoMapper invoiceDtoMapper, InvoiceProductRepository invoiceProductRepository) {
    this.securityService = securityService;
    this.invoiceDtoMapper = invoiceDtoMapper;
    this.invoiceProductRepository = invoiceProductRepository;
  }

  @Override
  public List<InvoiceProductDto> getAllInvoiceProducts() {
    Long companyId = securityService.getLoggedInUser().companyDto().id();

    return invoiceProductRepository.findAll()
      .stream()
      .filter(invoiceProduct -> invoiceProduct.getInvoice().getCompany().getId().equals(companyId))
      .map(invoiceProduct -> invoiceDtoMapper.apply(invoiceProduct))
      .sorted(Comparator.comparing(invoiceProductDto -> invoiceProductDto.invoiceDto().invoiceDate(),Comparator.reverseOrder()))
      .toList();
  }
}
