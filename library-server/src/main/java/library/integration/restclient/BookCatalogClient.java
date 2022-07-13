package library.integration.restclient;

import library.config.ApplicationProperties;
import library.domain.book.Book;
import library.exception.CustomErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class BookCatalogClient {
  @Autowired
  private ApplicationProperties applicationProperties;
  RestTemplate restTemplate = new RestTemplate();

  public Book getBookDetailByIsbn(String isbn) {
    Book book = new Book();
    try{
      book = restTemplate.getForObject(applicationProperties.getProductCatalogUrl() + "{isbn}", Book.class, isbn);
    } catch (Exception ex){
      throw new CustomErrorException(ex.getMessage());
    }
    return book;
  }
}
