package org.foo.services.impl;

import org.foo.dto.InvoiceDto;
import org.foo.dto.InvoiceProductDto;
import org.foo.dto.ProductDto;
import org.foo.enums.InvoiceStatus;
import org.foo.enums.InvoiceType;
import org.foo.mapper.InvoiceDtoMapper;
import org.foo.models.ClientVendor;
import org.foo.models.Company;
import org.foo.models.Invoice;
import org.foo.repository.ClientVendorRepository;
import org.foo.repository.CompanyRepository;
import org.foo.repository.InvoiceRepository;
import org.foo.services.CompanyService;
import org.foo.services.InvoiceProductService;
import org.foo.services.InvoiceService;
import org.foo.services.ProductService;
import org.springframework.stereotype.Service;

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

  public InvoiceServiceImpl(InvoiceRepository invoiceRepository, InvoiceDtoMapper invoiceDtoMapper, InvoiceProductService invoiceProductService, CompanyService companyService, ProductService productService, CompanyRepository companyRepository, ClientVendorRepository clientVendorRepository) {
    this.invoiceRepository = invoiceRepository;
    this.invoiceDtoMapper = invoiceDtoMapper;
    this.invoiceProductService = invoiceProductService;
    this.companyService = companyService;
    this.productService = productService;
    this.companyRepository = companyRepository;
    this.clientVendorRepository = clientVendorRepository;
  }

  @Override
  public List<InvoiceDto> getInvoicesForCompany(InvoiceType invoiceType) {
    Long companyId = companyService.getCompanyDtoByLoggedInUser().id();

    return invoiceRepository.findAllByInvoiceType(invoiceType).stream()
      .filter(invoice -> invoice.getCompany().getId().equals(companyId))
      .map(invoiceDtoMapper)
      .peek(this::calculatePriceDetailsForInvoice)
      .collect(Collectors.toList());
  }

  private void calculatePriceDetailsForInvoice(InvoiceDto invoiceDto) {
  }

  @Override
  public InvoiceDto findInvoiceById(Long id) {
    InvoiceDto invoiceDto = invoiceDtoMapper.apply(invoiceRepository.findById(id).get());

    if (invoiceDto!= null && invoiceDto.invoiceStatus().equals(InvoiceStatus.APPROVED)){
      calculatePriceDetailsForInvoice(invoiceDto);
    }
    return invoiceDto;
  }

  @Override
  public void deleteById(Long id) {
    Optional<Invoice> invoice = invoiceRepository.findById(id);


    if (invoice.isPresent()){
      invoice.get().setIsDeleted(true);
      invoiceRepository.save(invoice.get());
      invoiceProductService.deleteInvProductsByInvoiceId(id);
    }



  }

  @Override
  public void approveById(Long id) {
    Optional<Invoice> invoice = invoiceRepository.findById(id);

    if (invoice.isPresent()){

      List<InvoiceProductDto> invoiceProductDtoList = invoiceProductService.findInvoiceProductsByInvoiceId(id);

      if (invoice.get().getInvoiceType().equals(InvoiceType.SALES)){
        invoiceProductService.calculateProfitLoss(invoiceDtoMapper.apply(invoice.get()));
      }

      invoice.get().setInvoiceStatus(InvoiceStatus.APPROVED);
      invoice.get().setDate(LocalDate.now());
      updateProductQuantityInStock(invoiceDtoMapper.apply(invoice.get()));

      invoiceRepository.save(invoice.get());
    }


  }

  @Override
  public InvoiceDto createNewInvoice(InvoiceType invoiceType) {
    int number = getNumberOfInvoicesForCompany(invoiceType)+1;

    String invoiceNumber;



    if (invoiceType == InvoiceType.PURCHASE){
      invoiceNumber = String.format("P-%03d", number);
    }else {
      invoiceNumber = String.format("S-%03d", number);

    }

    InvoiceDto invoiceDto = new InvoiceDto(0L,invoiceNumber,InvoiceStatus.AWAITING_APPROVAL,InvoiceType.PURCHASE,LocalDate.now(),null,null,null,null,null);

    return invoiceDto;



  }

  @Override
  public int getNumberOfInvoicesForCompany(InvoiceType invoiceType) {
   Optional <Company> company = companyRepository.findById(companyService.getCompanyDtoByLoggedInUser().id());


    return invoiceRepository.findAllByCompanyAndInvoiceType(company.get(),invoiceType).size();
  }

  @Override
  public InvoiceDto save(InvoiceDto invoiceDto, InvoiceType invoiceType) {
    ClientVendor clientVendor = clientVendorRepository.findById(invoiceDto.clientVendorDto().id()).get();
    Company company = companyRepository.findById(invoiceDto.companyDto().id()).get();

    Invoice invoice = new Invoice(invoiceDto.invoiceNr(),invoiceDto.invoiceStatus(),invoiceDto.invoiceType(),invoiceDto.invoiceDate(),company,clientVendor,invoiceDto.price(),invoiceDto.tax(),invoiceDto.total());

    invoice.setInvoiceStatus(InvoiceStatus.AWAITING_APPROVAL);

    if(invoiceType == InvoiceType.PURCHASE){
      invoice.setInvoiceType(InvoiceType.PURCHASE);
    }else{
      invoice.setInvoiceType(InvoiceType.SALES);
    }

    invoiceRepository.save(invoice);

    return invoiceDtoMapper.apply(invoice);

  }

  @Override
  public void updateInvoice(InvoiceDto invoiceDto, Long invoiceId) {
    Company company = companyRepository.findById(invoiceDto.companyDto().id()).get();
    ClientVendor clientVendor = clientVendorRepository.findById(invoiceDto.clientVendorDto().id()).get();

    invoiceRepository.findById(invoiceId).ifPresent(invoice -> {
      invoice.setInvoiceNo(invoiceDto.invoiceNr());
      invoice.setInvoiceType(invoiceDto.invoiceType());
      invoice.setInvoiceStatus(invoiceDto.invoiceStatus());
      invoice.setDate(invoiceDto.invoiceDate());
      invoice.setCompany(company);
      invoice.setClientVendor(clientVendor);
      invoice.setPrice(invoiceDto.price());
      invoice.setTax(invoiceDto.tax());
      invoice.setTotal(invoiceDto.total());

      invoiceRepository.save(invoice);

        //Long id,
      //                         String invoiceNr,
      //                         InvoiceStatus invoiceStatus,
      //                         InvoiceType invoiceType,
      //                         LocalDate invoiceDate,
      //                         CompanyDto companyDto,
      //                         ClientVendorDto clientVendorDto,
      //                         BigDecimal price,
      //                         BigDecimal tax,
      //                         BigDecimal total
    });

  }

  @Override
  public void updateProductQuantityInStock(InvoiceDto invoice) {
    List<InvoiceProductDto> invoiceProductDtoList = invoiceProductService.findInvoiceProductsByInvoiceId(invoice.id());

    invoiceProductDtoList.forEach(invoiceProductDto -> {
      ProductDto productDto = productService.getProductById(invoiceProductDto.productDto().id());

      int remainingQuantity = 0;
      if (invoice.invoiceType().equals(InvoiceType.SALES)){
        remainingQuantity = productDto.quantityInStock() - invoiceProductDto.quantity();
      }else{
        remainingQuantity = productDto.quantityInStock() + invoiceProductDto.quantity();
      }

      ProductDto productDto1 = new ProductDto(productDto.id(), productDto.name(), remainingQuantity,productDto.lowLimitAlert(),productDto.productUnit(),productDto.categoryDto());
      InvoiceProductDto invoiceProductDto1 = new InvoiceProductDto(invoiceProductDto.id(), invoiceProductDto.quantity(),invoiceProductDto.price(),invoiceProductDto.tax(),invoiceProductDto.profitLoss(),invoiceProductDto.total(),remainingQuantity,invoiceProductDto.invoiceDto(),invoiceProductDto.productDto());

      invoiceProductService.updateInvoiceProduct(invoiceProductDto1);
      productService.updateProduct(productDto.id(), productDto1);
    });



  }

  @Override
  public List<InvoiceDto> getLast3InvoicesForCompany() {
    Long companyId = companyService.getCompanyDtoByLoggedInUser().id();

    return invoiceRepository.findAll().stream()
      .filter(invoice -> invoice.getCompany().getId().equals(companyId))
      .sorted(Comparator.comparing(invoice -> invoice.getDate()))
      .limit(3)
      .map(invoiceDtoMapper)
      .peek(this::calculatePriceDetailsForInvoice)
      .toList();
  }
}
