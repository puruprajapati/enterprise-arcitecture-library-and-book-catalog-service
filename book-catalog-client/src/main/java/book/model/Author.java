package book.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Author {

  private String firstName;

  private String lastName;

  private String contact;
  private String bio;

  public Author(String firstName, String lastName){
    this.firstName = firstName;
    this.lastName = lastName;
  }
}
