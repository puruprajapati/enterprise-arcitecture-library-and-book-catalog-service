package book.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookCopyDTO {
  private String scanCode;
  private boolean isAvailable = true;

  public BookCopyDTO(String scanCode) {
    this.scanCode = scanCode;
    this.isAvailable = true;
  }
}
