package org.foo.dto;

import org.foo.enums.ClientVendorType;
import org.foo.models.Address;

public record ClientVendorDto(Long id,
                              String clientVendorName,
                              String phone,
                              String website,
                              ClientVendorType clientVendorType,
                              Address addressDto,
                              CompanyDto companyDto
                              ) {
}
