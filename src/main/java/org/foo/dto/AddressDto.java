package org.foo.dto;

public record AddressDto(Long id,
                         String addressLine1,
                         String addressLine2,
                         String city,
                         String state,
                         String country,
                         String zipCode) {
}
