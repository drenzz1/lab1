package org.foo.dto;

import org.foo.enums.ClientVendorType;
import org.foo.models.Address;

public record ClientVendorDto(Long id,
                              String clientVendorName,
                              String phone,
                              String website,
                              ClientVendorType clientVendorType,
                              String addressLine1,
                              String addressLine2,
                              String city,
                              String state,
                              String country,
                              String zipCode,
                              CompanyDto companyDto
                              ) {
}
