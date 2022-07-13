package library.domain;

import library.domain.Customer;
import library.domain.Librarian;
import library.exception.CustomErrorException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {
  private Customer customer;
  @BeforeEach
  public void setUp() throws Exception{
    customer = new Customer("EA001", "testUser@mail.com", "Harry", "Winks", "610114844", "1000 North 4th street", "Fairfield", "IOWA", 52557);
  }

  @Test
  public void whenReserveBookShouldCreateReserveEntry(){
    String bookISBNNo = "EA-0001";
    customer.reserveBook(bookISBNNo);
    assertThat(customer.getReservationEntries().get(0).getIsbn(), equalTo(bookISBNNo));
  }

  @Test
  public void whenReserveMultipleBookShouldCreateMultipleReserveEntry(){
    String bookISBNNo = "EA-0001";
    customer.reserveBook(bookISBNNo);
    bookISBNNo = "EA-0002";
    customer.reserveBook(bookISBNNo);
    assertThat(customer.getReservationEntries(), hasSize(2));
  }

  @Test
  public void whenCheckoutBookShouldCreateCheckoutEntry(){
    String bookISBNNo = "EA-0001";
    String scanCode = "SCA00001223";
    LocalDate dueDate = LocalDate.now().plusDays(21);
    int maxBookThatCanBeCheckout = 4;
    customer.checkOutBook(bookISBNNo, scanCode,dueDate,maxBookThatCanBeCheckout);
    assertThat(customer.getCheckoutEntries().get(0).getScanCode(), equalTo(scanCode));
  }

  @Test
  public void whenCheckoutMultipleBookShouldCreateMultipleCheckoutEntry(){
    String bookISBNNo = "EA-0001";
    String scanCode = "SCA00001223";
    LocalDate dueDate = LocalDate.now().plusDays(21);
    int maxBookThatCanBeCheckout = 4;
    customer.checkOutBook(bookISBNNo, scanCode,dueDate,maxBookThatCanBeCheckout);
    scanCode = "SCA00001224";
    customer.checkOutBook(bookISBNNo, scanCode,dueDate,maxBookThatCanBeCheckout);
    assertThat(customer.getCheckoutEntries(), hasSize(2));
  }

  @Test
  public void afterMaxCheckoutCustomerNotAllowFurtherCheckout(){
    String bookISBNNo = "EA-0001";
    String scanCode = "SCA00001223";
    LocalDate dueDate = LocalDate.now().plusDays(21);
    int maxBookThatCanBeCheckout = 4;
    customer.checkOutBook(bookISBNNo, scanCode,dueDate,maxBookThatCanBeCheckout);
    scanCode = "SCA00001224";
    customer.checkOutBook(bookISBNNo, scanCode,dueDate,maxBookThatCanBeCheckout);
    scanCode = "SCA00001227";
    customer.checkOutBook(bookISBNNo, scanCode,dueDate,maxBookThatCanBeCheckout);
    scanCode = "SCA00001228";
    customer.checkOutBook(bookISBNNo, scanCode,dueDate,maxBookThatCanBeCheckout);
    scanCode = "SCA00001229";

    String finalScanCode = scanCode;
    Throwable exception = assertThrows(CustomErrorException.class, ()->{
      customer.checkOutBook(bookISBNNo, finalScanCode,dueDate,maxBookThatCanBeCheckout);
    });
    assertTrue(exception.getMessage().contains("Customer already exceed maximum books for checking out"));
  }

  @Test
  public void throwExceptionIfTryToReturnInvalidBook(){
    String bookISBNNo = "EA-0001";
    String scanCode = "SCA00001223";
    Throwable exception = assertThrows(CustomErrorException.class, ()->{
      customer.returnBook(bookISBNNo, scanCode);
    });
    assertTrue(exception.getMessage().contains("Information provided is not valid"));
  }

  @Test
  public void whenReturnBookSetReturnBookFlagToTrue(){
    String bookISBNNo = "EA-0001";
    String scanCode = "SCA00001223";
    LocalDate dueDate = LocalDate.now().plusDays(21);
    int maxBookThatCanBeCheckout = 4;
    customer.checkOutBook(bookISBNNo, scanCode,dueDate,maxBookThatCanBeCheckout);
    customer.returnBook(bookISBNNo, scanCode);
    assertThat(customer.getCheckoutEntries().get(0).isReturned(), equalTo(true));
  }

  @Test
  public void whenPayFeeCreatePaymentEntry(){
    Librarian librarian = new Librarian();
    double amount = 100;
    customer.payFee(amount, librarian);
    assertThat(customer.getPaymentEntries().get(0).getAmount(), closeTo(100, 0.01));
  }
}
