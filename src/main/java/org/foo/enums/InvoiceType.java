package org.foo.enums;

import lombok.Getter;

@Getter
public enum InvoiceType {

    PURCHASE("Purchase Invoice"),
    SALES("Sales Invoice");

    private String value;

    InvoiceType(String value) {
        this.value = value;
    }

}
