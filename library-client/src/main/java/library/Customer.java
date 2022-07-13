package library;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
  private String email;
  private String customerNumber;
  private String firstName;
  private String lastName;
  private String telephone;
  private Address address;

}

