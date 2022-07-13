package library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutstandingAmountPerCustomerDTO {
  private String customerNumber;
  private String name;
  private double outstandingFee;
}
