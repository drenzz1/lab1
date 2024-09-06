package org.foo.mapper;

import org.foo.dto.ClientVendorDto;
import org.foo.models.ClientVendor;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ClientVendorMapper implements Function<ClientVendor, ClientVendorDto> {
  @Override
  public ClientVendorDto apply(ClientVendor clientVendor) {
    return null;
  }
}
