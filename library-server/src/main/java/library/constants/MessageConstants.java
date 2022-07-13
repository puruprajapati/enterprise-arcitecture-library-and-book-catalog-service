package library.constants;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MessageConstants {
  public static final String EMAIL_REQUIRED = "Email is required";
  public static final String INVALID_CUSTOMER = "Invalid customer number";
  public static final String BOOK_NOT_FOUND = "Book not found";
  public static final String NOT_ABLE_TO_CHECKOUT = "Customer already exceed maximum books for checking out";
  public static final String INVALID_INFORMATION = "Information provided is not valid. Can't proceed further";

  public static final String CUSTOMER_DELETED = "Customer is deleted";
  public static final String RESERVED_BOOK = "Book reserved successfully";
  public static final String CHECKED_OUT = "Book checked out successfully";
  public static final String RETURNED_BOOK = "Book returned successfully";
  public static final String PAYMENT_MADE = "Payment successfully made";
}
