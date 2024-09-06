package org.foo.dto;

import org.foo.enums.Months;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PaymentDto(Long id,
                         Integer year,
                         Months month,
                         LocalDate paymentDate,
                         BigDecimal amount,
                         boolean isPaid,
                         String companyCC,
                         String description,
                         CompanyDto companyDto
                         ) {
}
