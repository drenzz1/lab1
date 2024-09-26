package org.foo.services.impl;

import org.foo.dto.ClientVendorDto;
import org.foo.dto.InvoiceDto;
import org.foo.dto.ProductDto;
import org.foo.enums.InvoiceStatus;
import org.foo.enums.InvoiceType;
import org.foo.exceptions.InvoiceNotFoundException;
import org.foo.mapper.InvoiceDtoMapper;
import org.foo.models.ClientVendor;
import org.foo.models.Company;
import org.foo.models.Invoice;
import org.foo.repository.ClientVendorRepository;
import org.foo.repository.CompanyRepository;
import org.foo.repository.InvoiceRepository;
import org.foo.services.*;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class InvoiceServiceImpl implements InvoiceService {
  private final InvoiceRepository invoiceRepository;
  private final InvoiceDtoMapper invoiceDtoMapper;
  private final InvoiceProductService invoiceProductService;
  private final CompanyService companyService;
  private final ProductService productService;
  private final CompanyRepository companyRepository;
  private final ClientVendorRepository clientVendorRepository;
  private final SecurityService securityService;

  public InvoiceServiceImpl(InvoiceRepository invoiceRepository, InvoiceDtoMapper invoiceDtoMapper, InvoiceProductService invoiceProductService, CompanyService companyService, ProductService productService, CompanyRepository companyRepository, ClientVendorRepository clientVendorRepository, SecurityService securityService) {
    this.invoiceRepository = invoiceRepository;
    this.invoiceDtoMapper = invoiceDtoMapper;
    this.invoiceProductService = invoiceProductService;
    this.companyService = companyService;
    this.productService = productService;
    this.companyRepository = companyRepository;
    this.clientVendorRepository = clientVendorRepository;
    this.securityService = securityService;
  }

  @Override
  public InvoiceDto findById(Long id) {
    Invoice invoice = invoiceRepository.findById(id).orElseThrow(()->new InvoiceNotFoundException("Invoice not found "+id));
    InvoiceDto invoiceDto = invoiceDtoMapper.apply(invoice);
    invoiceDto.setTotal(calcInvoiceGrandTotal(invoiceDto));
    invoiceDto.setTax(calcInvoiceTax(invoiceDto));
    invoiceDto.setPrice(calcInvoiceSubTotal(invoiceDto));
    return invoiceDto;
  }

  @Override
  public InvoiceDto save(InvoiceDto invoiceDto, InvoiceType purchase) {
    return null;
  }

  @Override
  public InvoiceDto update(InvoiceDto invoiceDto, long id) {
    Invoice invoice = invoiceRepository.findById(id).orElseThrow(() -> new InvoiceNotFoundException("Invoice not found " + id));
    ClientVendor clientVendor =clientVendorRepository.findById(invoiceDto.getClientVendor().id()).get();
    invoice.setClientVendor(clientVendor);
    invoice.setInvoiceStatus(InvoiceStatus.AWAITING_APPROVAL);
    invoiceRepository.save(invoice);
    return invoiceDtoMapper.apply(invoice);
  }

  @Override
  public void delete(Long id) {
    Optional<Invoice> toDelete = invoiceRepository.findById(id);

    if (toDelete.isPresent()) {
      toDelete.get().setIsDeleted(true);
      invoiceRepository.save(toDelete.get());
    }

  }

  @Override
  public List<InvoiceDto> findAllInvoicesByCompany() {
    return null;
  }

  @Override
  public void approveInvoice(Long id) {
    Optional<Invoice> toApprove = invoiceRepository.findById(id);
    if (toApprove.isPresent()) {
      toApprove.get().setInvoiceStatus(InvoiceStatus.APPROVED);
      invoiceRepository.save(toApprove.get());
    }

  }

  @Override
  public String assignNextPurchaseInvoiceNumberByCompany(String companyTitle) {
    Long invoiceNumber = invoiceRepository.countAllByInvoiceTypeAndCompanyTitle(InvoiceType.PURCHASE,
      companyTitle).orElse(0L) + 1;
    DecimalFormat df = new DecimalFormat("000");
    return "P-" + df.format(invoiceNumber);
  }

  @Override
  public InvoiceDto getNewInvoice(InvoiceType invoiceType, String companyTitle) {
    Invoice newInvoice = new Invoice();

    if (invoiceType == InvoiceType.PURCHASE) {
      newInvoice.setInvoiceNo(assignNextPurchaseInvoiceNumberByCompany(companyTitle));
    } else if (invoiceType == InvoiceType.SALES) {
      newInvoice.setInvoiceNo(generateSaleInvoiceNo(companyTitle));
    } else {
      System.err.println("INVOICE NUMBER COULD NOT BE ASSIGNED");
    }

    newInvoice.setDate(LocalDate.now());

    return invoiceDtoMapper.apply(newInvoice);


  }

  @Override
  public String generateSaleInvoiceNo(String company) {
    return null;
  }

  @Override
  public List<InvoiceDto> findAllSaleInvoicesByCompany(String company) {
    return invoiceRepository.findAllSaleInvoicesByCompany(company, Sort.by("invoiceNo").descending())
      .stream()
      .map(invoiceDtoMapper)
      .collect(Collectors.toList());
  }
  private BigDecimal calcInvoiceSubTotal(InvoiceDto invoice) {
      return null;
  }

  private BigDecimal calcInvoiceGrandTotal(InvoiceDto invoice) {
    return null;
  }

  private BigDecimal calcInvoiceTax(InvoiceDto invoice) {
    return null;
  }

  @Override
  public List<InvoiceDto> findAllPurchaseInvoicesByCompany(String companyTitle) {
    return invoiceRepository.findAllByCompanyTitle(companyTitle).stream()
      .filter(f -> f.getInvoiceType() == InvoiceType.PURCHASE)
      .map(invoiceDtoMapper)
      .collect(Collectors.toList());
  }

  @Override
  public void updatePurchaseInvoiceVendor(InvoiceDto invoiceDto) {

    ClientVendor newVendor = clientVendorRepository.findById(invoiceDto.getClientVendor().id()).get();

    System.out.println("newVendor = " + newVendor);
    Invoice oldInvoice = invoiceRepository.findById(invoiceDto.getId())
      .orElseThrow(() -> new InvoiceNotFoundException("Could not find Invoice with id " + invoiceDto.getId()));

    oldInvoice.setClientVendor(newVendor);
    invoiceRepository.save(oldInvoice);

  }

  @Override
  public List<InvoiceDto> fetchAllApprovedPurchaseInvoicesOfCompany(String companyTitle) {
    return invoiceRepository.fetchApprovedPurchaseInvoicesOfCompany(companyTitle).stream()
      .map(invoiceDtoMapper)
      .collect(Collectors.toList());
  }

  @Override
  public List<InvoiceDto> fetchAllApprovedSalesInvoicesOfCompany(String companyTitle) {
    return invoiceRepository.fetchApprovedSalesInvoicesOfCompany(companyTitle).stream()
      .map(invoiceDtoMapper)
      .collect(Collectors.toList());
  }

  @Override
  public List<InvoiceDto> lastThreeApprovedInvoicesOfCompany(String companyTitle) {
    return invoiceRepository.lastApprovedInvoicesOfCompany(companyTitle).stream()
      .map((m -> {
        InvoiceDto asDto = invoiceDtoMapper.apply(m);
        asDto.setPrice(calcInvoiceSubTotal(asDto));
        asDto.setTax(calcInvoiceTax(asDto));
        asDto.setTotal(calcInvoiceGrandTotal(asDto));

        System.out.println("asDto.getPrice() = " + asDto.getPrice());
        System.out.println("asDto.getTax() = " + asDto.getTax());
        System.out.println("asDto.getTotal() = " + asDto.getTotal());
        return asDto;
      }))
      .limit(3)
      .collect(Collectors.toList());
  }
}
