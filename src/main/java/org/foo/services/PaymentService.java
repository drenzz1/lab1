package org.foo.services;

import org.foo.dto.PaymentDto;

import java.util.List;

public interface PaymentService {

  PaymentDto createPayment(PaymentDto paymentDTO);
  List<PaymentDto> getAllPayments();

}
