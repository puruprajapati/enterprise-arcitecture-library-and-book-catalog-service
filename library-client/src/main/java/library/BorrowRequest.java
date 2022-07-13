package library;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BorrowRequest {
  private String customerNumber;
  private String isbn;
  private String scanCode;
}
