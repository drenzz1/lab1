package org.foo.mapper;

import org.foo.dto.PaymentDto;
import org.foo.models.Payment;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class PaymentDtoMapper implements Function<Payment, PaymentDto> {
  private final CompanyDtoMapper companyDtoMapper;

  public PaymentDtoMapper(CompanyDtoMapper companyDtoMapper) {
    this.companyDtoMapper = companyDtoMapper;
  }

  @Override
  public PaymentDto apply(Payment payment) {
    return new PaymentDto(payment.getId(),
      payment.getYear(),
      payment.getMonth(),
      payment.getPaymentDate(),
      payment.getAmount(),
      payment.isPaid(),
      payment.getCompanyCc(),
      payment.getDescription(),
      companyDtoMapper.apply(payment.getCompany()));
  }
}
