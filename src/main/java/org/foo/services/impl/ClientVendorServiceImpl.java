package org.foo.services.impl;

import org.foo.dto.ClientVendorDto;
import org.foo.enums.ClientVendorType;
import org.foo.exceptions.ClientVendorNotFoundException;
import org.foo.mapper.ClientVendorMapper;
import org.foo.mapper.CompanyDtoMapper;
import org.foo.models.Category;
import org.foo.models.ClientVendor;
import org.foo.models.Company;
import org.foo.repository.ClientVendorRepository;
import org.foo.repository.CompanyRepository;
import org.foo.services.ClientVendorService;
import org.foo.services.CompanyService;
import org.foo.services.SecurityService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientVendorServiceImpl implements ClientVendorService {
  private final ClientVendorRepository clientVendorRepository;
  private final ClientVendorMapper clientVendorDtoMapper;
  private final SecurityService securityService;
  private final CompanyService companyService;
  private final CompanyDtoMapper companyDtoMapper;
  private final CompanyRepository  companyRepository;

  public ClientVendorServiceImpl(ClientVendorRepository clientVendorRepository, ClientVendorMapper clientVendorDtoMapper, SecurityService securityService, CompanyService companyService, CompanyDtoMapper companyDtoMapper, CompanyRepository companyRepository) {
    this.clientVendorRepository = clientVendorRepository;
    this.clientVendorDtoMapper = clientVendorDtoMapper;
    this.securityService = securityService;
    this.companyService = companyService;
    this.companyDtoMapper = companyDtoMapper;
    this.companyRepository = companyRepository;
  }

  @Override
  public List<ClientVendorDto> listAllClientVendors() {
    return null;
  }

  @Override
  public List<ClientVendorDto> listClientVendorsForCompany(ClientVendorType clientVendorType) {
return null;
  }

  @Override
  public void save(ClientVendorDto clientVendorDto) throws ClientVendorNotFoundException {
  }

  @Override
  public void update(Long clientVendorId, ClientVendorDto clientVendorDto) throws ClientVendorNotFoundException {

    clientVendorRepository.findById(clientVendorId).ifPresent(clientVendor -> {
      clientVendor.setClientVendorType(clientVendorDto.clientVendorType());
      clientVendor.setPhone(clientVendorDto.phone());
      clientVendor.setWebsite(clientVendorDto.website());
      clientVendor.setAddressLine1(clientVendorDto.addressLine1());
      clientVendor.setAddressLine2(clientVendorDto.addressLine2());
      clientVendor.setCity(clientVendorDto.city());
      clientVendor.setState(clientVendorDto.state());
      clientVendor.setCountry(clientVendorDto.country());
      clientVendor.setZipCode(clientVendorDto.zipCode());
      clientVendor.setCompany(companyRepository.findById(clientVendorDto.companyDto().id()).get());
      clientVendorRepository.save(clientVendor);
    });

  }

  @Override
  public ClientVendorDto findById(Long id) throws ClientVendorNotFoundException {
    return clientVendorDtoMapper.apply(clientVendorRepository.findById(id).get());
  }

  @Override
  public void deleteById(Long id) throws ClientVendorNotFoundException {
    ClientVendor clientVendor = clientVendorRepository.findById(id).get();
    clientVendor.setIsDeleted(true);
    clientVendorRepository.save(clientVendor);

  }

  @Override
  public boolean companyNameExists(ClientVendorDto clientVendorDto) throws ClientVendorNotFoundException {
     return true;

  }
}
