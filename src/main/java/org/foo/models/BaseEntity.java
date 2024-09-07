package org.foo.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@MappedSuperclass
@EntityListeners(BaseEntityListener.class)
public class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,updatable = false)
    public LocalDateTime insertDateTime=LocalDateTime.now();

    @Column(nullable = false,updatable = false)
    public Long insertUserId=1L;

    @Column(nullable = false)
    public LocalDateTime lastUpdateDateTime=LocalDateTime.now();

    @Column(nullable = false)
    public Long lastUpdateUserId=1L;

    private Boolean isDeleted = false;
}
