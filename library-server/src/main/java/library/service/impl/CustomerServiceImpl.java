package library.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import library.config.ApplicationProperties;
import library.domain.Customer;
import library.domain.Librarian;
import library.domain.book.Book;
import library.domain.book.BookCopy;
import library.dto.BorrowedAndLateReturnedBookDTO;
import library.dto.CustomerDTO;
import library.dto.NotifyCheckoutBookDTO;
import library.dto.OutstandingAmountPerCustomerDTO;
import library.exception.ResourceNotFoundException;
import library.integration.activemq.NotifyCheckout;
import library.integration.mailing.IMailSender;
import library.integration.restclient.BookCatalogClient;
import library.repo.CustomerRepo;
import library.repo.LibrarianRepo;
import library.repo.ReservationRepo;
import library.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static library.constants.MessageConstants.*;

@Service
public class CustomerServiceImpl implements CustomerService {
  @Autowired
  private CustomerRepo customerRepo;

  @Autowired
  private ReservationRepo reservationRepo;

  @Autowired
  private LibrarianRepo librarianRepo;

  @Autowired
  private NotifyCheckout notifyCheckout;

  @Autowired
  private BookCatalogClient bookCatalogClient;

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private ApplicationProperties applicationProperties;

  @Autowired
  private IMailSender mailSender;

  @Override
  public List<CustomerDTO> getAllCustomers() {
    return customerRepo.findAll()
      .stream()
      .map(customer -> modelMapper.map(customer, CustomerDTO.class))
      .collect(Collectors.toList());
  }

  @Override
  public CustomerDTO addCustomer(CustomerDTO customerDTO) {
    Customer customer = customerRepo.save(modelMapper.map(customerDTO, Customer.class));
    return modelMapper.map(customer, CustomerDTO.class);
  }

  @Override
  public CustomerDTO updateCustomer(String customerNumber, CustomerDTO customerDTO) throws ResourceNotFoundException {
    Optional<Customer> customer = customerRepo.findByCustomerNumber(customerNumber);
    if(customer.isPresent()){
      customer.get().setFirstName(customerDTO.getFirstName());
      customer.get().setLastName(customerDTO.getLastName());
      customer.get().setTelephone(customerDTO.getTelephone());
      customer.get().setEmail(customerDTO.getEmail());
      return modelMapper.map(customerRepo.save(customer.get()), CustomerDTO.class);
    }
    throw new ResourceNotFoundException(INVALID_CUSTOMER);
  }

  @Override
  public void deleteCustomer(String isbn) throws ResourceNotFoundException {
    Optional<Customer> customer = customerRepo.findByCustomerNumber(isbn);
    if(customer.isPresent()){
      customerRepo.delete(customer.get());
    } else {
      throw new ResourceNotFoundException(INVALID_CUSTOMER);
    }
  }

  @Override
  public CustomerDTO getCustomerByCustomerNumber(String customerNo) throws ResourceNotFoundException {
    Optional<Customer> customer = customerRepo.findByCustomerNumber(customerNo);
    if(customer.isPresent()){
      return modelMapper.map(customer.get(), CustomerDTO.class);
    } else {
      throw new ResourceNotFoundException(INVALID_CUSTOMER);
    }
  }

  @Override
  public List<CustomerDTO> searchCustomerBYEmailOrNumberOrName(String searchText) {
    return customerRepo.searchCustomerByCustNoEmailName(searchText)
      .stream()
      .map(customer -> modelMapper.map(customer, CustomerDTO.class))
      .collect(Collectors.toList());
  }

  @Override
  public void reserveBook(String customerNumber, String isbn) throws ResourceNotFoundException {
    Optional<Customer> customer = customerRepo.findByCustomerNumber(customerNumber);
    if(customer.isPresent()){
      customer.get().reserveBook(isbn);
      customerRepo.save(customer.get());
    } else {
      throw new ResourceNotFoundException(INVALID_CUSTOMER);
    }
  }

  @Override
  public void checkOutBook(String customerNumber, String isbn) throws ResourceNotFoundException, JsonProcessingException {
    Book book = bookCatalogClient.getBookDetailByIsbn(isbn);
    List<BookCopy> bookCopyList = book.getBookCopies().stream().filter(b->b.isAvailable()).collect(Collectors.toList());
    if(bookCopyList.size() == 0){
      throw new ResourceNotFoundException(BOOK_NOT_FOUND);
    }
    String scanCode = bookCopyList.get(0).getScanCode();
    LocalDate dueDate = LocalDate.now().plusDays(applicationProperties.getMaxDaysBorrow());
    int maxBookThatCanBeCheckout = applicationProperties.getMaxCheckoutBook();
    Optional<Customer> customer = customerRepo.findByCustomerNumber(customerNumber);
    if(customer.isPresent()){
      customer.get().checkOutBook(isbn, scanCode, dueDate, maxBookThatCanBeCheckout);
      customerRepo.save(customer.get());
      notifyCheckout.pushCheckoutMsgToActiveMQ(new NotifyCheckoutBookDTO(isbn, scanCode));
    } else {
      throw new ResourceNotFoundException(INVALID_CUSTOMER);
    }
  }

  @Override
  public void returnBook(String customerNumber, String isbn, String scanCode) throws ResourceNotFoundException {
    Optional<Customer> customer = customerRepo.findByCustomerNumber(customerNumber);
    if(customer.isPresent()){
      // check whether this book is reserved by another customer, if yes send mail to that customer
      List<Long> customerList = reservationRepo.findByIsbnAndActiveTrue(isbn);
      for(long customerId: customerList){
        Optional<Customer> cust = customerRepo.findById(customerId);
        if(cust.isPresent()){
          mailSender.sendMail(cust.get().getEmail(), "Book " + isbn + " is available", "Book available");
        }
      }
      customer.get().returnBook(isbn, scanCode);
      customerRepo.save(customer.get());
    } else {
      throw new ResourceNotFoundException(INVALID_CUSTOMER);
    }
  }

  @Override
  public void payFee(String customerNumber,double amount) throws ResourceNotFoundException {
    // get librarian in system
    Librarian librarian = librarianRepo.findAll().get(0);
    Optional<Customer> customer = customerRepo.findByCustomerNumber(customerNumber);
    if(customer.isPresent()){
      customer.get().payFee(amount, librarian);
      customerRepo.save(customer.get());
    } else {
      throw new ResourceNotFoundException(INVALID_CUSTOMER);
    }
  }

  @Override
  public List<OutstandingAmountPerCustomerDTO> getOutstandingFeePerCustomer(String customerNumber) {
    List<Object[]> tempObject = customerRepo.getOutstandingAmountPerCustomer(customerNumber, applicationProperties.getFeePerDay());
    List<OutstandingAmountPerCustomerDTO> resultSet = new ArrayList<>();
    for(Object[] result: tempObject){
      OutstandingAmountPerCustomerDTO dataSet = new OutstandingAmountPerCustomerDTO();
      dataSet.setCustomerNumber((String) result[0]);
      dataSet.setName((String) result[1]);
      dataSet.setOutstandingFee((Double) result[2]);
      resultSet.add(dataSet);
    }
    return resultSet;
  }

  @Override
  public void getAllCustomerData(String customerNumber) {

  }

  @Override
  public List<BorrowedAndLateReturnedBookDTO> getAllBorrowedAndLateReturnedBook() {
    List<Object[]> tempObject = customerRepo.getAllBorrowedAndLateReturnedBook();
    List<BorrowedAndLateReturnedBookDTO> resultSet = new ArrayList<>();
    for(Object[] result: tempObject){
      BorrowedAndLateReturnedBookDTO dataSet = new BorrowedAndLateReturnedBookDTO();
      dataSet.setIsbn((String) result[0]);
      dataSet.setScanCode((String) result[1]);
      dataSet.setCustomerName((String) result[2]);
      dataSet.setBorrowedDate((Date) result[3]);
      dataSet.setReturnedDate((Date) result[4]);
      dataSet.setReturnedDate((Date) result[5]);
      dataSet.setStatus((String) result[6]);
      resultSet.add(dataSet);
    }
    return resultSet;
  }
}
