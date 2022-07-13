package book.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection="Books")
@Data
@NoArgsConstructor
public class Book {
  @Id
  private String id;

  @NotBlank
  @NotNull
  @NotEmpty
  @Size(max = 100)
  @Indexed(unique = true)
  private String isbn;

  @NotBlank
  @NotNull
  @NotEmpty
  @Size(max = 100)
  private String title;

  private LocalDateTime publishedDate;

  private String publisher;

  private List<Author> authors;

  private List<BookCopy> bookCopies = new ArrayList<>();

  public Book(String isbn, String title, List<Author> authors, String scanCode){
    this.isbn = isbn;
    this.title = title;
    this.authors = authors;
    this.bookCopies.add(new BookCopy(scanCode));
  }

  public Book(String isbn, String title, List<Author> authors, String publisher, LocalDateTime publishedDate, String scanCode){
    this.isbn = isbn;
    this.title = title;
    this.authors = authors;
    this.publisher = publisher;
    this.publishedDate = publishedDate;
    this.bookCopies.add(new BookCopy(scanCode));
  }

  public Book(String isbn, String title, List<Author> authors, String publisher, LocalDateTime publishedDate, List<BookCopy> bookCopies){
    this.isbn = isbn;
    this.title = title;
    this.authors = authors;
    this.publisher = publisher;
    this.publishedDate = publishedDate;
    this.bookCopies = bookCopies;
  }

  public void addCopy(String scanCode){
    bookCopies.add(new BookCopy(scanCode));
  }

  public void addCopy(String scanCode, boolean isAvailable){
    bookCopies.add(new BookCopy(scanCode, isAvailable));
  }

  public void updateCheckedOutBook(String scanCode){
    BookCopy bookCopy = bookCopies.stream().filter(book -> book.getScanCode().equals(scanCode)).findFirst().get();
    bookCopy.setAvailable(false);
  }



}
