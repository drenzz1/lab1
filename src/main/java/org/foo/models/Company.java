package org.foo.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.foo.enums.CompanyStatus;
import org.hibernate.annotations.Where;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "companies")
@Where(clause = "is_deleted=false")
public class Company extends BaseEntity{

    @Column(unique = true)
    private String title;

    private String phone;

    private String website;

  private String addressLine1;

  private String addressLine2;


  private String city;


  private String state;


  private String country;


  private String zipCode;

    @Enumerated(EnumType.STRING)
    private CompanyStatus companyStatus;



}
