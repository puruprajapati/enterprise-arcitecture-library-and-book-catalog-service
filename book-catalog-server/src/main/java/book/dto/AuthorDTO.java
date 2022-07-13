package book.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDTO {

  private String firstName;

  private String lastName;

  private String contact;
  private String bio;

  public AuthorDTO(String firstName, String lastName){
    this.firstName = firstName;
    this.lastName = lastName;
  }
}
