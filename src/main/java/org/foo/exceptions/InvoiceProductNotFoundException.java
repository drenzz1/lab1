package org.foo.exceptions;

public class InvoiceProductNotFoundException extends RuntimeException {
  public InvoiceProductNotFoundException(String s) {
    super(s);
  }
}
