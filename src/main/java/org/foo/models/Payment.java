package org.foo.models;

import jakarta.persistence.*;
import org.foo.enums.Months;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "payments")
public class Payment extends BaseEntity {
  private int year;
  private BigDecimal amount;
  private LocalDate paymentDate;
  private boolean isPaid;
  private String companyStripeId;
  @Enumerated(EnumType.STRING)
  private Months month;
  @ManyToOne
  @JoinColumn(name = "company_id")
  private Company company;

  // Getters and setters
}
