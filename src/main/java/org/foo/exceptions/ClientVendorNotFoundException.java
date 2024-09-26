package org.foo.exceptions;

public class ClientVendorNotFoundException extends RuntimeException {
  public ClientVendorNotFoundException(String message) {
    super(message);
  }
}
