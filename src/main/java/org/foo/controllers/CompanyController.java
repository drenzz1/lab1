package org.foo.controllers;

import org.foo.dto.CompanyDto;
import org.foo.services.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/company")
public class CompanyController {
  private final CompanyService companyService;

  public CompanyController(CompanyService companyService) {
    this.companyService = companyService;
  }

  @GetMapping("/list")
  public List<CompanyDto> getAllCompanies(){
    return companyService.getAllCompanies();
  }
  @PostMapping("/create")
  public ResponseEntity<String> createNewCompany(@RequestBody CompanyDto companyDto){
    companyService.save(companyDto);
    return ResponseEntity.status(HttpStatus.OK).build();
  }
  @GetMapping("/list/{id}")
  public CompanyDto getCompanyById(@PathVariable("id")Long id){
    return companyService.findCompanyById(id);
  }
  @PutMapping("/edit/{id}")
  public ResponseEntity<String> editCompany(@PathVariable("id")Long id , @RequestBody CompanyDto companyDto){
    companyService.update(id,companyDto);
    return ResponseEntity.status(HttpStatus.OK).build();
  }
  @GetMapping("/activate/{id}")
  public ResponseEntity<String> activateCompany(@PathVariable("id")Long id){
    companyService.activateCompany(id);
    return ResponseEntity.status(HttpStatus.OK).build();
  }
  @GetMapping("/deactivate/{id}")
  public ResponseEntity<String> deactivateCompany(@PathVariable("id")Long id){
    companyService.deactivateCompany(id);
    return ResponseEntity.status(HttpStatus.OK).build();

  }
}
