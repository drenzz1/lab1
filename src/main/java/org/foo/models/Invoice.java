package org.foo.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.foo.enums.InvoiceStatus;
import org.foo.enums.InvoiceType;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "invoices")
@Where(clause = "is_deleted=false")
public class Invoice extends BaseEntity{


  private String invoiceNo;

  @Enumerated(EnumType.STRING)
  private InvoiceStatus invoiceStatus;

  @Enumerated(EnumType.STRING)
  private InvoiceType invoiceType;

  private LocalDate date;

  @ManyToOne
  private Company company;

  @ManyToOne
  private ClientVendor clientVendor;

  @OneToMany(mappedBy = "invoice")
  private List<InvoiceProduct> invoiceProducts;



}
