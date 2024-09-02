package org.foo.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.foo.enums.ProductUnit;
import org.hibernate.annotations.Where;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
@Where(clause = "is_deleted=false")
public class Product extends BaseEntity{

    private String name;

    private int quantityInStock;

    private int lowLimitAlert;

    @Enumerated(EnumType.STRING)
    private ProductUnit productUnit;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

}
