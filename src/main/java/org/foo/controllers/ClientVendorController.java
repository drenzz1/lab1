package org.foo.controllers;

import org.foo.dto.ClientVendorDto;
import org.foo.services.ClientVendorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/client-vendors")
public class ClientVendorController {

  private final ClientVendorService clientVendorService;

  public ClientVendorController(ClientVendorService clientVendorService) {
    this.clientVendorService = clientVendorService;
  }

  @GetMapping("/list")
  public List<ClientVendorDto> getAllClientVendors(){
    return clientVendorService.listAllClientVendors();
  }

  @PostMapping("/create")
  public ResponseEntity<String> createClientVendor(@RequestBody ClientVendorDto clientVendorDto){
    clientVendorService.save(clientVendorDto);
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @PutMapping("/edit/{id}")
  public ResponseEntity<String> editClientVendor(@PathVariable("id")Long id , @RequestBody ClientVendorDto clientVendorDto){
    clientVendorService.update(id,clientVendorDto);

    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<String> deleteClientVendor(@PathVariable("id")Long id){

    clientVendorService.deleteById(id);

    return ResponseEntity.status(HttpStatus.OK).build();
  }
}
