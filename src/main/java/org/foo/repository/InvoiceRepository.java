package org.foo.repository;

import org.foo.enums.InvoiceStatus;
import org.foo.enums.InvoiceType;
import org.foo.models.Company;
import org.foo.models.Invoice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

  List<Invoice> findAllByCompanyAndInvoiceType(Company company, InvoiceType invoiceType);
  List<Invoice> findAllByInvoiceType(InvoiceType invoiceType);

    Optional<Invoice> findAllByCompanyTitle(String company);

  Optional<Long> countAllByInvoiceTypeAndCompanyTitle(InvoiceType invoiceType, String companyTitle);

  @Query("SELECT i FROM Invoice i WHERE i.invoiceType = 'SALES' AND i.company.title = ?1")
  List<Invoice> findAllSaleInvoicesByCompany(String companyTitle, Sort sort);

  @Query("select e from Invoice e where e.company.title = ?1 and e.isDeleted = false order by e.invoiceNo desc")
  List<Invoice> findAllByCompany(String company);

  Optional<Invoice> findById(Long id);

  @Query(value = "select count(e) from Invoice e where e.invoiceType = ?1 and e.company.title = ?2")
  Optional<Long> countAllByInvoiceTypeAndCompany(InvoiceType invoiceType, String companyTitle);



  @Query(value = "select count(e) from Invoice e where e.invoiceType = 'SALES' and e.company.title = ?1")
  int countTotalSaleInvoices(String company);


  @Query(value="select e from Invoice e where e.company.title = ?1 and " +
    "e.invoiceType = 'PURCHASE' and " +
    "e.invoiceStatus = 'APPROVED'")
  List<Invoice> fetchApprovedPurchaseInvoicesOfCompany(String companyTitle);

  @Query(value="select e from Invoice e where e.company.title = ?1 and " +
    "e.invoiceType = 'SALES' and " +
    "e.invoiceStatus = 'APPROVED'")
  List<Invoice> fetchApprovedSalesInvoicesOfCompany(String companyTitle);


  @Query(value="select inv from Invoice inv " +
    "where inv.company.title = ?1 " +
    "and inv.invoiceStatus = 'APPROVED' " +
    "order by inv.date desc")
  List<Invoice> lastApprovedInvoicesOfCompany(String companyTitle);


}
