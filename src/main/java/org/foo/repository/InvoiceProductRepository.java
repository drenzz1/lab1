package org.foo.repository;

import org.foo.enums.InvoiceStatus;
import org.foo.enums.InvoiceType;
import org.foo.models.Company;
import org.foo.models.Invoice;
import org.foo.models.InvoiceProduct;
import org.foo.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceProductRepository extends JpaRepository<InvoiceProduct, Long> {



  @Query("SELECT ip FROM InvoiceProduct ip WHERE ip.invoice.company.title = ?1")
  List<InvoiceProduct> findByCompanyTitle(String companyTitle);

  @Query("SELECT ip FROM InvoiceProduct ip WHERE  ip.invoice.id = ?1")
  List<InvoiceProduct> findAllInvoiceProduct(Long invoiceId);

  @Query(value = "select ip.product from InvoiceProduct ip where ip.invoice.id = ?1")
  List<Product> getProductListByInvoiceId(Long id);

  @Query(value = "select ip from InvoiceProduct ip where ip.invoice.id = ?1")
  List<InvoiceProduct> getInvoiceProductsByInvoiceId(Long id);

  List<InvoiceProduct> findAllByInvoice_InvoiceStatusAndInvoice_Company(InvoiceStatus invoiceStatus, Company company);
}
