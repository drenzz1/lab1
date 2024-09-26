package org.foo.mapper;

import org.foo.dto.AddressDto;
import org.foo.models.Address;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class AddressDtoMapper implements Function<Address, AddressDto> {
  @Override
  public AddressDto apply(Address address) {
    return new AddressDto(address.getId(),
      address.getAddressLine1(),
      address.getAddressLine2(),
      address.getCity(),
      address.getState(),
      address.getCountry(),
      address.getZipCode());
  }
}
