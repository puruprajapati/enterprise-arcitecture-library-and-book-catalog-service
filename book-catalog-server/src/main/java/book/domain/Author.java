package book.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Author {
  @NotBlank
  @Size(max = 100)
  private String firstName;
  @NotBlank
  @Size(max = 100)
  private String lastName;
  @Size(max = 100)
  private String contact;
  @Size(max = 1000)
  private String bio;

  public Author(String firstName, String lastName){
    this.firstName = firstName;
    this.lastName = lastName;
  }
}
