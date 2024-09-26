package org.foo.services;

import org.foo.dto.ClientVendorDto;
import org.foo.enums.ClientVendorType;
import org.foo.exceptions.ClientVendorNotFoundException;

import java.util.List;

public interface ClientVendorService {
  List<ClientVendorDto> listAllClientVendors();
  List<ClientVendorDto> listClientVendorsForCompany(ClientVendorType clientVendorType);
  void save(ClientVendorDto clientVendorDto) throws ClientVendorNotFoundException;
  void update(Long id ,ClientVendorDto clientVendorDto)throws ClientVendorNotFoundException;

  ClientVendorDto findById(Long id)throws ClientVendorNotFoundException;

  void deleteById(Long id)throws ClientVendorNotFoundException;

  boolean companyNameExists(ClientVendorDto clientVendorDto)throws ClientVendorNotFoundException;
}
