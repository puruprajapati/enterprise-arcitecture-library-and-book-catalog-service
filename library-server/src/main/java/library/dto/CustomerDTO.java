package library.dto;

import library.domain.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {
  private String email;
  private String customerNumber;
  private String firstName;
  private String lastName;
  private String telephone;
  private AddressDTO address;

}
