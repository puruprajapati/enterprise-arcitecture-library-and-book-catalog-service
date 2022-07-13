package library.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import library.dto.BorrowedAndLateReturnedBookDTO;
import library.dto.CustomerDTO;
import library.dto.OutstandingAmountPerCustomerDTO;
import library.exception.ResourceNotFoundException;

import java.util.List;

public interface CustomerService {

  // customer
  List<CustomerDTO> getAllCustomers();
  CustomerDTO addCustomer(CustomerDTO customerDTO);
  CustomerDTO updateCustomer(String customerNumber, CustomerDTO customerDTO) throws ResourceNotFoundException;
  void deleteCustomer(String customerNumber) throws ResourceNotFoundException;
  CustomerDTO getCustomerByCustomerNumber(String customerNo) throws ResourceNotFoundException;

  List<CustomerDTO> searchCustomerBYEmailOrNumberOrName(String searchText);

  // reservation
  void reserveBook(String customerNumber,String isbn) throws ResourceNotFoundException;

  // borrow
  void checkOutBook(String customerNumber, String isbn) throws ResourceNotFoundException, JsonProcessingException;
  void returnBook(String customerNumber, String isbn, String scanCode) throws ResourceNotFoundException, JsonProcessingException;
  void payFee(String customerNumber, double amount) throws ResourceNotFoundException;

  // report
  OutstandingAmountPerCustomerDTO getOutstandingFeePerCustomer(String customerNumber);
  void getAllCustomerData(String customerNumber);
  List<BorrowedAndLateReturnedBookDTO> getAllBorrowedAndLateReturnedBook();

}
