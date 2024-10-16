package org.foo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "addresses")
@Where(clause = "is_deleted=false")
public class Address extends BaseEntity{


    private String addressLine1;

    private String addressLine2;


    private String city;


    private String state;


    private String country;


    private String zipCode;

}
