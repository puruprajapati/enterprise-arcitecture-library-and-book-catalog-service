package library;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutstandingFeePerCustomer {
  private String customerNumber;
  private String name;
  private double outstandingFee;
}
