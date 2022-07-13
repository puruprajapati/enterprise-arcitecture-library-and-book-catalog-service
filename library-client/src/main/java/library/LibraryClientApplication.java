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
//    restTemplate.postForLocation(serverUrl, customer);

    // search customer by name
    customerList = restTemplate.getForObject(serverUrl + "/search/{searchText}", Customers.class, "puru");
    System.out.println("******************* search book via name *******************");
    System.out.println(customerList);

    // reserve book
    BorrowRequest reqForReservation = new BorrowRequest();
    reqForReservation.setCustomerNumber("EA001");
    reqForReservation.setIsbn("A00100BOOL8");
    System.out.println("******************* reserve a book *******************");
    restTemplate.postForLocation(serverUrl + "/reserve-book", reqForReservation);

    // checkout book - not found book
    BorrowRequest reqForCheckout = new BorrowRequest();
    reqForCheckout.setCustomerNumber("EA001");
    reqForCheckout.setScanCode("11-11111");
    System.out.println("******************* checkout a book *******************");
    restTemplate.postForLocation(serverUrl + "/checkout-book", reqForCheckout);

    // checkout book - invalid customer
    reqForCheckout.setCustomerNumber("EA001");
    reqForCheckout.setScanCode("48-56882");
    System.out.println("******************* checkout a book *******************");
    restTemplate.postForLocation(serverUrl + "/checkout-book", reqForCheckout);

    // checkout book - valid
    reqForCheckout.setCustomerNumber("EA002");
    reqForCheckout.setScanCode("48-56882");
    System.out.println("******************* checkout a book *******************");
    restTemplate.postForLocation(serverUrl + "/checkout-book", reqForCheckout);;

  }
}
