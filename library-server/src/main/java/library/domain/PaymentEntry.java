package library.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PaymentEntry extends BaseEntity {
  private LocalDate paymentDate;
  private double amount;
  @OneToOne
  @JoinColumn(name = "received_by")
  private Librarian receivedBy;

}
