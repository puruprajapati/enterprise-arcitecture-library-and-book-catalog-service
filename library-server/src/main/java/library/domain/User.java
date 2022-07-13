package library.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class User extends BaseEntity {
  @NotEmpty(message = "First name is required")
  private String firstName;
  @NotEmpty(message = "Last name is required")
  private String lastName;
  private String telephone;
  @Embedded
  private Address address;
}
