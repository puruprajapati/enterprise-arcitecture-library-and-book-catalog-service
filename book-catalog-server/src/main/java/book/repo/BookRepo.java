package book.repo;

import book.domain.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepo extends MongoRepository<Book, String> {
  public Optional<Book> findByIsbn(String isbn);
  public Optional<List<Book>> findByTitle(String title);
  public Optional<List<Book>> findByPublisher(String publisher);
}
