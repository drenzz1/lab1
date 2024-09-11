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



  List<InvoiceProduct> findAllByInvoice_Id(Long id);

  @Query("select ip from InvoiceProduct ip where ip.invoice.invoiceType = ?1 and ip.product = ?2 and ip.invoice.invoiceStatus = 'APPROVED' order by ip.id")
  List<InvoiceProduct> findApprovedInvoiceProductsForProduct(InvoiceType type, Product product);

  Optional<InvoiceProduct> findByProduct_Id(Long id);
}
