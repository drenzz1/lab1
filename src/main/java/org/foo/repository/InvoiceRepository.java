package org.foo.repository;

import org.foo.enums.InvoiceStatus;
import org.foo.enums.InvoiceType;
import org.foo.models.Company;
import org.foo.models.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

  Invoice findInvoiceById(Long id);
  List<Invoice> findInvoicesByCompanyAndInvoiceType(Company company, InvoiceType invoiceType);
  List<Invoice> findInvoicesByCompanyAndInvoiceStatus(Company company, InvoiceStatus invoiceStatus);
  List<Invoice> findInvoicesByCompanyAndInvoiceStatusOrderByDateDesc(Company company, InvoiceStatus invoiceStatus);
  Integer countAllByCompanyAndClientVendor_Id(Company company, Long clientVendorId);

}
