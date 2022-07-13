package book.controller;

import book.dto.BookDTO;
import book.exception.ResourceNotFoundException;
import book.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static book.constants.MessageConstants.BOOK_DELETED;

@RestController
@RequestMapping("api/books")
public class BookController {
  @Autowired
  private BookService bookService;

  @GetMapping
  public ResponseEntity<?> getAllBooks(){
    List<BookDTO> bookDTOS = bookService.getAllBooks();
    return new ResponseEntity<>(bookDTOS, HttpStatus.OK);
  }

  @GetMapping("/{isbn}")
  public ResponseEntity<?> getBookByIsbn(@PathVariable(name="isbn") String isbn) throws ResourceNotFoundException {
    BookDTO bookDTO = bookService.getBookByIsbn(isbn);
    return ResponseEntity.ok().body(bookDTO);
  }

  @GetMapping("/search")
  public ResponseEntity<?> searchBook(@RequestParam String searchBy, @RequestParam String searchValue){
    Collection<BookDTO> bookDTOCollection = new ArrayList<>();
    if(searchBy.equals("title")){
      bookDTOCollection = bookService.searchBookByTitle(searchValue);
    } else if(searchBy.equals("publisher")){
      bookDTOCollection = bookService.searchBookByPublisher(searchValue);
    }
    return ResponseEntity.ok().body(bookDTOCollection);
  }

  @PostMapping
  public ResponseEntity<?> addBook(@Valid @RequestBody BookDTO bookDTO){
    BookDTO book = bookService.addBook(bookDTO);
    return new ResponseEntity<BookDTO>(book, HttpStatus.CREATED);
  }

  @PutMapping("/{isbn}")
  public ResponseEntity<?> updateBook(@PathVariable String isbn, @RequestBody BookDTO bookDTO) throws ResourceNotFoundException {
    BookDTO book = bookService.updateBook(isbn, bookDTO);
    return new ResponseEntity<BookDTO>(book, HttpStatus.OK);
  }

  @DeleteMapping("/{isbn}")
  public ResponseEntity<?> deleteBook(@PathVariable String isbn) throws ResourceNotFoundException {
    bookService.deleteBook(isbn);
    return new ResponseEntity<>(BOOK_DELETED, HttpStatus.OK);
  }


}
