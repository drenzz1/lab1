package org.foo.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.foo.enums.Gender;
import org.hibernate.annotations.Where;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "users")
@Where(clause = "is_deleted=false")
public class User extends BaseEntity {

  private String firstName;
  private String lastName;
  @Column(unique = true,nullable = false)
  private String userName;
  private String passWord;
  private boolean enabled;
  private String phone;

  @ManyToOne
  @JoinColumn(name = "role_id")
  private Role role;

  @Enumerated(EnumType.STRING)
  private Gender gender;


}
