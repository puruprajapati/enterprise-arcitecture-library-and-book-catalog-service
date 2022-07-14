package book.dto;

import book.domain.Author;
import book.domain.BookCopy;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
  private String isbn;
  private String title;

  private LocalDateTime publishedDate;

  private String publisher;

  private List<AuthorDTO> authors;

  private List<BookCopyDTO> bookCopies = new ArrayList<>();
}
