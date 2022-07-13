package library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BorrowedAndLateReturnedBookDTO {
  private String isbn;
  private String scanCode;
  private String customerName;
  private Date borrowedDate;
  private Date returnedDate;
  private Date dueDate;
  private String status;
}
