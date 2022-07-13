package book.service;

import book.dto.BookDTO;
import book.exception.ResourceNotFoundException;

import java.util.Collection;
import java.util.List;

public interface BookService {
  List<BookDTO> getAllBooks();
  BookDTO addBook(BookDTO bookDTO);
  BookDTO updateBook(String isbn, BookDTO bookDTO) throws ResourceNotFoundException;
  void deleteBook(String isbn) throws ResourceNotFoundException;
  BookDTO getBookByIsbn(String isbn) throws ResourceNotFoundException;

  void updateCheckedOutBook(String isbn, String scanCode) throws ResourceNotFoundException;

  Collection<BookDTO> searchBookByTitle(String searchValue);

  Collection<BookDTO> searchBookByPublisher(String searchValue);
}
