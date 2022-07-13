package library.domain;

import library.dto.CustomerOutstandingFeeDTO;
import library.exception.CustomErrorException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static library.constants.MessageConstants.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
//@SqlResultSetMapping(
//  name="getOutstandingAmountPerCustomerMapping",
//  classes = {
//    @ConstructorResult(
//      targetClass = CustomerOutstandingFeeDTO.class,
//      columns = {
//        @ColumnResult(name="customer_number"),
//        @ColumnResult(name="name")
//      }
//    )
//  }
//)
//@NamedNativeQuery(name="Customer.getOutstandingAmountPerCustomer",
//  query="select\n" +
//    "       c.customer_number,\n" +
//    "       max(concat(c.first_name, ' ', c.last_name)) as name\n" +
////    "       (sum(current_date - (ce.due_date)::date) * 0.5) as outstanding_fee\n" +
//    "from customers c\n" +
//    "inner join checkout_entry ce on c.id = ce.customer_id\n" +
//    "where ce.is_returned = false\n" +
//    "and ce.due_date < current_date\n" +
//    "and c.customer_number = ?1 " +
//    "group by c.customer_number",
//  resultSetMapping = "getOutstandingAmountPerCustomerMapping")

@Table(name="customers")
public class Customer extends User {
  @NotEmpty(message = EMAIL_REQUIRED)
  @Email
  private String email;
  @Column(unique = true)
  private String customerNumber;
  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name="customer_id")
  private List<CheckoutEntry> checkoutEntries = new ArrayList<>();
  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name="customer_id")
  private List<PaymentEntry> paymentEntries = new ArrayList<>();
  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name="customer_id")
  private List<ReservationEntry> reservationEntries = new ArrayList<>();

  public Customer(String customerNumber, String email, String firstName, String lastName, String telePhone, String street, String city, String state, int zipCode){
    this.customerNumber = customerNumber;
    this.email = email;
    this.setFirstName(firstName);
    this.setLastName(lastName);
    this.setTelephone(telePhone);
    this.setAddress(new Address(street, city, state, zipCode));
  }

  public void reserveBook(String isbn){
    ReservationEntry reservationEntry = new ReservationEntry();
    reservationEntry.setIsbn(isbn);
    List<ReservationEntry> reservationEntriesWithSameBook = reservationEntries.stream().filter(re -> re.getIsbn().equals(isbn)).collect(Collectors.toList());
    if(reservationEntriesWithSameBook.size() == 0){
      reservationEntries.add(reservationEntry);
    }
  }

  public void checkOutBook(String isbn, String scanCode, LocalDate dueDate, int maxBookThatCanBeCheckout){
    if(ableToCheckout(maxBookThatCanBeCheckout)){
      CheckoutEntry checkoutEntry = new CheckoutEntry();
      checkoutEntry.setIsbn(isbn);
      checkoutEntry.setBorrowedDate(LocalDate.now());
      checkoutEntry.setDueDate(dueDate);
      checkoutEntry.setScanCode(scanCode);
      checkoutEntries.add(checkoutEntry);
    } else {
      throw new CustomErrorException(NOT_ABLE_TO_CHECKOUT);
    }
  }

  public void returnBook(String isbn, String scanCode){
    Optional<CheckoutEntry> checkoutEntry = checkoutEntries.stream().filter(ce->ce.getIsbn().equals(isbn) && ce.getScanCode().equals(scanCode)).findFirst();
    if(checkoutEntry.isPresent()){
      checkoutEntry.get().setReturned(true);
    } else {
      throw new CustomErrorException(INVALID_INFORMATION);
    }
  }

  public void payFee(double amount, Librarian librarian){
    PaymentEntry paymentEntry = new PaymentEntry(LocalDate.now(), amount, librarian);
    paymentEntries.add(paymentEntry);
  }

  public boolean ableToCheckout(int maxBookThatCanBeCheckout){
    List<CheckoutEntry> chkoutEntries = checkoutEntries.stream().filter(ce -> ce.isReturned() == false).collect(Collectors.toList());
    if(chkoutEntries.size() >= maxBookThatCanBeCheckout){
      return false;
    } else {
      return true;
    }
  }
}
