package org.foo.dto;

import java.math.BigDecimal;

public record InvoiceProductDto(Long id,
                                Integer quantity,
                                BigDecimal price,
                                Integer tax,
                                BigDecimal total,
                                BigDecimal profitLoss,
                                Integer remainingQuantity,
                                InvoiceDto invoiceDto,
                                ProductDto productDto
                                ) {
}
