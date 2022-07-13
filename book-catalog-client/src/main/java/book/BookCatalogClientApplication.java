package book;

import book.model.Book;
import book.model.BookList;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@SpringBootApplication
public class BookCatalogClientApplication implements CommandLineRunner {
  RestTemplate restTemplate = new RestTemplate();
  private String serverUrl = "http://localhost:8080/api/books";

  public static void main(String[] args) {
    SpringApplication.run(BookCatalogClientApplication.class, args);
  }


  @Override
  public void run(String... args) throws Exception {
    // Get all books
//    var bookList = restTemplate.getForObject(serverUrl, BookList.class);
//    System.out.println("******************* Get all books *******************");
//    System.out.println(bookList);

    // Get book by isbn
    Book book = restTemplate.getForObject(serverUrl + "/{isbn}", Book.class, "23-11451");
    System.out.println("******************* Get book by isbn *******************");
    System.out.println(book);

    // update book by isbn
    book.setPublisher("Nima Publication");
    restTemplate.put(serverUrl + "/{isbn}", book, "23-11451");
    System.out.println("******************* Update book by isbn *******************");
    System.out.println(book);


  }
}
