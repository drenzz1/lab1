package org.foo.dto;

import org.foo.enums.ProductUnit;

public record ProductDto(Long id,
                         String name,
                         Integer quantityInStock,
                         Integer lowLimitAlert,
                         ProductUnit productUnit,
                         CategoryDto categoryDto
                         ) {
}
