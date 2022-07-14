package library;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class LibraryClientApplication implements CommandLineRunner {

  RestTemplate restTemplate = new RestTemplate();

  private String serverUrl = "http://localhost:9090/api/customers";

  public static void main(String[] args) {
    SpringApplication.run(LibraryClientApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    // Get all customers
    Customers customerList = restTemplate.getForObject(serverUrl, Customers.class);
    System.out.println("******************* Get all books *******************");
    System.out.println(customerList);

    // Get customer by customer number
    Customer customer = restTemplate.getForObject(serverUrl + "/{customerNumber}", Customer.class, "EA001");
    System.out.println("******************* Get customer by customer number *******************");
    System.out.println(customer);

    // update customer
    customer.setFirstName("Scarlett");
    restTemplate.put(serverUrl + "/{isbn}", customer, "EA001");
    System.out.println("******************* Update customer by customer number *******************");
    System.out.println(customer);

    // create new customer
    Customer newCustomer = new Customer();
    customer.setCustomerNumber("EA005");
    customer.setFirstName("Puru");
    customer.setLastName("Prajapati");
    customer.setTelephone("6418192461");
    customer.setEmail("pprajapati@miu.edu");
    customer.setAddress(new Address("1000N 4th street", "Fairfield", "IOWA", 52557));
    restTemplate.postForLocation(serverUrl, customer);

    // search customer by name
    customerList = restTemplate.getForObject(serverUrl + "/search/{searchText}", Customers.class, "puru");
    System.out.println("******************* search book via name *******************");
    System.out.println(customerList);

    // reserve book
    BorrowRequest reqForReservation = new BorrowRequest();
    reqForReservation.setCustomerNumber("EA001");
    reqForReservation.setIsbn("11-11111");
    System.out.println("******************* reserve a book *******************");
    restTemplate.postForLocation(serverUrl + "/reserve-book", reqForReservation);

    BorrowRequest reqForCheckout = new BorrowRequest();


    // checkout book - not found book
    try {
      reqForCheckout.setCustomerNumber("EA001");
      reqForCheckout.setIsbn("11-11111");
      System.out.println("******************* checkout a book *******************");
      var response = restTemplate.postForLocation(serverUrl + "/checkout-book", reqForCheckout);
      System.out.println(response);
    } catch(Exception ex) {
      System.out.println(ex.getMessage());
    }

    // checkout book - invalid customer
    try {
      reqForCheckout.setCustomerNumber("EA001");
      reqForCheckout.setIsbn("48-56882");
      System.out.println("******************* checkout a book *******************");
      var response = restTemplate.postForLocation(serverUrl + "/checkout-book", reqForCheckout);
      System.out.println(response);
    } catch (Exception ex){
      System.out.println(ex.getMessage());
    }


    // checkout book - valid
    try {
      reqForCheckout.setCustomerNumber("EA002");
      reqForCheckout.setIsbn("48-56882");
      System.out.println("******************* checkout a book *******************");
      var response = restTemplate.postForLocation(serverUrl + "/checkout-book", reqForCheckout);
      System.out.println(response);
    } catch (Exception ex){
      System.out.println(ex.getMessage());
    }

    // return book
    try {
      reqForCheckout.setCustomerNumber("EA001");
      reqForCheckout.setIsbn("28-12331");
      reqForCheckout.setScanCode("A00100BOOL3");
      System.out.println("******************* return a book *******************");
      var response = restTemplate.postForLocation(serverUrl + "/return-book", reqForCheckout);
      System.out.println(response);
    } catch (Exception ex){
      System.out.println(ex.getMessage());
    }

    // get outstanding fee for customer
    OutstandingFeePerCustomer outstandingFeePerCustomer = restTemplate.getForObject(serverUrl + "/reports/getOutstandingAmountPerCustomer/{customerNumber}", OutstandingFeePerCustomer.class, "EA001");
    System.out.println("******************* Get outstanding fee *******************");
    System.out.println(outstandingFeePerCustomer);

    // pay fee for the customer
    try {
      Payment payment = new Payment();
      payment.setCustomerNumber("EA001");
      payment.setAmount(outstandingFeePerCustomer.getOutstandingFee());
      System.out.println("******************* pay fee *******************");
      var response = restTemplate.postForLocation(serverUrl + "/pay-fee", payment);
      System.out.println(response);
    } catch (Exception ex){
      System.out.println(ex.getMessage());
    }

  }
}
