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
    Long companyId = securityService.getLoggedInUser().companyDto().id();
    Company company = companyRepository.findById(companyId).get();

    return clientVendorRepository.findAllByCompany(company).stream()
      .map(clientVendorDtoMapper).toList();
  }

  @Override
  public List<ClientVendorDto> listClientVendorsForCompany(ClientVendorType clientVendorType) {
    Long companyId = securityService.getLoggedInUser().companyDto().id();

    return listAllClientVendors().stream()
      .filter(clientVendorDto -> clientVendorDto.companyDto().id().equals(companyId))
      .filter(clientVendorDto -> clientVendorDto.clientVendorType().equals(clientVendorType))
      .collect(Collectors.toList());

  }

  @Override
  public void save(ClientVendorDto clientVendorDto) throws ClientVendorNotFoundException {
    Company company = companyRepository.findById(clientVendorDto.companyDto().id()).get();

    ClientVendor clientVendor = new ClientVendor(clientVendorDto.clientVendorName(),clientVendorDto.phone(),clientVendorDto.website(),clientVendorDto.clientVendorType(),clientVendorDto.addressDto(),company);

    clientVendorRepository.save(clientVendor);

  }

  @Override
  public void update(Long clientVendorId, ClientVendorDto clientVendorDto) throws ClientVendorNotFoundException {

    clientVendorRepository.findById(clientVendorId).ifPresent(clientVendor -> {
      clientVendor.setClientVendorType(clientVendorDto.clientVendorType());
      clientVendor.setPhone(clientVendorDto.phone());
      clientVendor.setWebsite(clientVendorDto.website());
      clientVendor.setAddress(clientVendorDto.addressDto());
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
    Long companyId = securityService.getLoggedInUser().companyDto().id();
    Company company = companyRepository.findById(companyId).get();

    ClientVendor existingClientVendor = clientVendorRepository.findByClientVendorNameAndCompany(clientVendorDto.clientVendorName(), company);
    if (existingClientVendor == null) return true;

    return !existingClientVendor.getId().equals(clientVendorDto.id());


  }
}
