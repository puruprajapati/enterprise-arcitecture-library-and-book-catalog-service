package library.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CheckoutEntry extends BaseEntity {
  private LocalDate borrowedDate;
  private LocalDate dueDate;
  private LocalDate returnDate;
  private String isbn;
  private String scanCode;
  private boolean isReturned = false;

}
