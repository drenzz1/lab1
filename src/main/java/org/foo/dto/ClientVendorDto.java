package org.foo.dto;

import org.foo.enums.ClientVendorType;

public record ClientVendorDto(Long id,
                              String clientVendorName,
                              String phone,
                              String website,
                              ClientVendorType clientVendorType,
                              AddressDto addressDto,
                              CompanyDto companyDto,
                              boolean hasInvoice) {
}
