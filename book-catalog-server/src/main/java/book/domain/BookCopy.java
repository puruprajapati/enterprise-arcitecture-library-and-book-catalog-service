package book.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookCopy {
  @Indexed(unique = true)
  private String scanCode;
  private boolean isAvailable = true;

  public BookCopy(String scanCode) {
    this.scanCode = scanCode;
    this.isAvailable = true;
  }
}
