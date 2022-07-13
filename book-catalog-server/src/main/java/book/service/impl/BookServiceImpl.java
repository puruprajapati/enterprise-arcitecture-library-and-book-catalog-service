package book.service.impl;

import book.domain.Book;
import book.dto.BookDTO;
import book.exception.ResourceNotFoundException;
import book.repo.BookRepo;
import book.service.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static book.constants.MessageConstants.INVALID_ISBN;

@Service
public class BookServiceImpl implements BookService {
  @Autowired
  private BookRepo bookRepo;

  @Autowired
  private ModelMapper modelMapper;

  @Override
  public List<BookDTO> getAllBooks() {
    return bookRepo.findAll()
      .stream()
      .map(book -> modelMapper.map(book, BookDTO.class))
      .collect(Collectors.toList());
  }

  @Override
  public BookDTO addBook(BookDTO bookDTO) {
    Book book = bookRepo.save(modelMapper.map(bookDTO, Book.class));
    return modelMapper.map(book, BookDTO.class);
  }

  @Override
  public BookDTO updateBook(String isbn, BookDTO bookDTO) throws ResourceNotFoundException {
    Optional<Book> book = bookRepo.findByIsbn(isbn);
    if(book.isPresent()){
      book.get().setTitle(bookDTO.getTitle());
      book.get().setPublisher(bookDTO.getPublisher());
      book.get().setPublishedDate(bookDTO.getPublishedDate());
      return modelMapper.map(bookRepo.save(book.get()), BookDTO.class);
    }
    throw new ResourceNotFoundException(INVALID_ISBN);
  }

  @Override
  public void deleteBook(String isbn) throws ResourceNotFoundException {
    Optional<Book> book = bookRepo.findByIsbn(isbn);
    if(book.isPresent()){
      bookRepo.delete(book.get());
    } else {
      throw new ResourceNotFoundException(INVALID_ISBN);
    }
  }

  @Override
  public BookDTO getBookByIsbn(String isbn) throws ResourceNotFoundException {
    Optional<Book> book = bookRepo.findByIsbn(isbn);
    if(book.isPresent()){
      return modelMapper.map(book.get(), BookDTO.class);
    } else {
      throw new ResourceNotFoundException(INVALID_ISBN);
    }
  }

  @Override
  public void updateCheckedOutBook(String isbn, String scanCode) throws ResourceNotFoundException {
    Optional<Book> book = bookRepo.findByIsbn(isbn);
    if(book.isPresent()){
      book.get().updateCheckedOutBook(scanCode);
      bookRepo.save(book.get());
    } else {
      throw new ResourceNotFoundException(INVALID_ISBN);
    }
  }
}
