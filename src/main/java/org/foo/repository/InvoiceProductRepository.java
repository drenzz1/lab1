package org.foo.repository;

import org.foo.enums.InvoiceStatus;
import org.foo.enums.InvoiceType;
import org.foo.models.Company;
import org.foo.models.Invoice;
import org.foo.models.InvoiceProduct;
import org.foo.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceProductRepository extends JpaRepository<InvoiceProduct, Long> {

  InvoiceProduct findInvoiceProductById(Long id);
  List<InvoiceProduct> findAllByInvoice(Invoice invoice);
  List<InvoiceProduct> findAllByInvoice_Id(Long id);
  List<InvoiceProduct> findAllByInvoice_InvoiceTypeAndInvoice_Company(InvoiceType invoiceType, Company company);
  List<InvoiceProduct> findAllByInvoice_InvoiceStatusAndInvoice_Company(InvoiceStatus invoiceStatus, Company company);
  List<InvoiceProduct> findInvoiceProductsByInvoiceInvoiceTypeAndProductAndRemainingQuantityNotOrderByIdAsc(InvoiceType invoiceType, Product product, Integer remainingQuantity);
  List<InvoiceProduct> findAllInvoiceProductByProductId(Long id);
}
