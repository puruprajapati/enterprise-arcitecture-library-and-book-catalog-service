package book.data.seed;

import book.domain.Author;
import book.domain.Book;
import book.repo.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {
  @Autowired
  BookRepo bookRepo;

  public List<Author> allAuthors = new ArrayList<Author>() {
    {
      add(new Author("Iron", "Man", "641-819-2123", "A happy man is he."));
      add(new Author("Incredible", "Hulk", "641-819-2123", "A happy wife is she."));
      add(new Author("Aqua", "Man", "641-819-3223", "Thinker of thoughts."));
      add(new Author("Bat", "Man", "641-819-2232", "Author of childrens' books."));
      add(new Author("Super", "Man", "641-819-2663", "Known for her clever style."));
      add(new Author("Captain", "America", "641-819-3223", "Thinker of thoughts."));
      add(new Author("Black", "Widow", "641-819-2232", "Author of childrens' books."));
      add(new Author("The", "Flash", "641-819-2663", "Known for her clever style."));
    }
  };

  List<Book> allBooks = new ArrayList<Book>() {
    {
      add(new Book("23-11451", "The Big Fish", Arrays.asList(allAuthors.get(0), allAuthors.get(1)), "A00100BOOK1"));
      add(new Book("28-12331", "Antarctica",Arrays.asList(allAuthors.get(2)), "A00100BOOK2"));
      add(new Book("99-22223", "Thinking Java", Arrays.asList(allAuthors.get(3)), "A00100BOOK3"));
      add(new Book("48-56882", "Jimmy's First Day of School", Arrays.asList(allAuthors.get(4)), "A00100BOOK4"));
      add(new Book("11-11111", "The Da Vinci Code", Arrays.asList(allAuthors.get(0), allAuthors.get(1)), "A00100BOOK5"));
      add(new Book("11-12331", "Digital Fortress", Arrays.asList(allAuthors.get(2)), "New Publisher", LocalDateTime.now(), "A00100BOOK6"));
      add(new Book("11-22223", "Inception", Arrays.asList(allAuthors.get(3)), "AX Publisher", LocalDateTime.now(), "A00100BOOK7"));
      add(new Book("11-56882", "Angels and Demon", Arrays.asList(allAuthors.get(4)), "PP Publisher",
        LocalDateTime.now().minusYears(100)
        .plusMonths(1)
        .plusWeeks(1)
        .plusDays(1), "A00100BOOK8"));
    }
  };

  @Override
  public void run(String... args) throws Exception {
      loadSeederData();
  }

  private void loadSeederData() {
    // create some copy in book
    allBooks.get(0).addCopy("A00100BOOL1");
    allBooks.get(0).addCopy("A00100BOOL2");
    allBooks.get(1).addCopy("A00100BOOL3", false);
    allBooks.get(1).addCopy("A00100BOOL4", false);
    allBooks.get(1).addCopy("A00100BOOL5", false);
    allBooks.get(3).addCopy("A00100BOOL6");
    allBooks.get(2).addCopy("A00100BOOL7");
    allBooks.get(2).addCopy("A00100BOOL8");

    // update available status
    allBooks.get(1).updateCheckedOutBook("A00100BOOK2");
    allBooks.get(3).updateCheckedOutBook("A00100BOOK4");
    allBooks.get(4).updateCheckedOutBook("A00100BOOK5");

    // save in database
    bookRepo.saveAll(allBooks);
  }


}
