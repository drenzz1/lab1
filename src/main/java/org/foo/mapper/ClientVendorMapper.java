package org.foo.mapper;

import org.foo.dto.ClientVendorDto;
import org.foo.models.ClientVendor;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ClientVendorMapper implements Function<ClientVendor, ClientVendorDto> {
  private final AddressDtoMapper addressDtoMapper;
  private final CompanyDtoMapper companyDtoMapper;

  public ClientVendorMapper(AddressDtoMapper addressDtoMapper, CompanyDtoMapper companyDtoMapper) {
    this.addressDtoMapper = addressDtoMapper;
    this.companyDtoMapper = companyDtoMapper;
  }

  @Override
  public ClientVendorDto apply(ClientVendor clientVendor) {

    return new ClientVendorDto(clientVendor.getId(),
      clientVendor.getClientVendorName(),
      clientVendor.getPhone(),
      clientVendor.getWebsite(),
      clientVendor.getClientVendorType(),
      addressDtoMapper.apply(clientVendor.getAddress()),
      companyDtoMapper.apply(clientVendor.getCompany()));
  }
}
