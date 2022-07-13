package library.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import library.dto.BorrowDTO;
import library.dto.CustomerDTO;
import library.dto.CustomerListDTO;
import library.exception.ResourceNotFoundException;
import library.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static library.constants.MessageConstants.*;

@RestController
@RequestMapping("api/customers")
public class CustomerController {
  @Autowired
  private CustomerService customerService;
  
  @GetMapping
  public ResponseEntity<?> getAllCustomers(){
    List<CustomerDTO> customerDTOS = customerService.getAllCustomers();
    CustomerListDTO customers = new CustomerListDTO(customerDTOS);
    return new ResponseEntity<>(customers, HttpStatus.OK);
  }

  @GetMapping("/{customerNumber}")
  public ResponseEntity<?> getCustomerByCustomerNumber(@PathVariable(name="customerNumber") String customerNumber) throws ResourceNotFoundException {
    CustomerDTO customerDTO = customerService.getCustomerByCustomerNumber(customerNumber);
    return ResponseEntity.ok().body(customerDTO);
  }

  @PostMapping
  public ResponseEntity<?> addBook(@Valid @RequestBody CustomerDTO customerDTO){
    CustomerDTO book = customerService.addCustomer(customerDTO);
    return new ResponseEntity<CustomerDTO>(book, HttpStatus.CREATED);
  }

  @PutMapping("/{customerNumber}")
  public ResponseEntity<?> updateBook(@PathVariable String customerNumber, @RequestBody CustomerDTO customerDTO) throws ResourceNotFoundException {
    CustomerDTO book = customerService.updateCustomer(customerNumber, customerDTO);
    return new ResponseEntity<CustomerDTO>(book, HttpStatus.OK);
  }

  @DeleteMapping("/{customerNumber}")
  public ResponseEntity<?> deleteBook(@PathVariable String customerNumber) throws ResourceNotFoundException {
    customerService.deleteCustomer(customerNumber);
    return new ResponseEntity<>(CUSTOMER_DELETED, HttpStatus.OK);
  }

  @GetMapping("/search/{searchText}")
  public ResponseEntity<?> searchCustomerByEmailOrNumberOrName(@PathVariable String searchText){
    List<CustomerDTO> customerDTOS = customerService.searchCustomerBYEmailOrNumberOrName(searchText);
    CustomerListDTO customers = new CustomerListDTO(customerDTOS);
    return new ResponseEntity<>(customers, HttpStatus.OK);
  }

  @PostMapping("/reserve-book")
  public ResponseEntity<?> reserveBook(@RequestBody BorrowDTO borrowDTO) throws ResourceNotFoundException {
    customerService.reserveBook(borrowDTO.getCustomerNumber(), borrowDTO.getIsbn());
    return new ResponseEntity<>(RESERVED_BOOK, HttpStatus.OK);
  }

  @PostMapping("/checkout-book")
  public ResponseEntity<?> checkoutBook(@RequestBody BorrowDTO borrowDTO) throws ResourceNotFoundException, JsonProcessingException {
    customerService.checkOutBook(borrowDTO.getCustomerNumber(), borrowDTO.getIsbn());
    return new ResponseEntity<>(CHECKED_OUT, HttpStatus.OK);
  }

  @PostMapping("/return-book")
//  public ResponseEntity<?> returnBook(@RequestParam String customerNumber, @RequestParam String isbn, @RequestParam String scanCode) throws ResourceNotFoundException {
  public ResponseEntity<?> returnBook(@RequestBody BorrowDTO borrowDTO) throws ResourceNotFoundException {
    customerService.returnBook(borrowDTO.getCustomerNumber(), borrowDTO.getIsbn(), borrowDTO.getScanCode());
    return new ResponseEntity<>(RETURNED_BOOK, HttpStatus.OK);
  }

  @PostMapping("/pay-fee")
  public ResponseEntity<?> payFee(@RequestParam String customerNumber, @RequestParam double amount) throws ResourceNotFoundException {
    customerService.payFee(customerNumber, amount);
    return new ResponseEntity<>(PAYMENT_MADE, HttpStatus.OK);
  }

  @GetMapping("/reports/getAllBorrowedAndLateReturnedBook")
  public ResponseEntity<?> getAllBorrowedAndLateReturnedBook(){
    return new ResponseEntity<>(customerService.getAllBorrowedAndLateReturnedBook(), HttpStatus.OK);
  }

  @GetMapping("/reports/getAllBorrowedAndLateReturnedBook/{customerNumber}")
  public ResponseEntity<?> getOutstandingAmountPerCustomer(@PathVariable String customerNumber){
    return new ResponseEntity<>(customerService.getOutstandingFeePerCustomer(customerNumber), HttpStatus.OK);
  }
}
