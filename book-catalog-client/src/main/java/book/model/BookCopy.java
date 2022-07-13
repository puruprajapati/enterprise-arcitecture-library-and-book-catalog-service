package book.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookCopy {
  private String scanCode;
  private boolean isAvailable = true;

  public BookCopy(String scanCode) {
    this.scanCode = scanCode;
    this.isAvailable = true;
  }
}
