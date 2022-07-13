package library.repo;

import library.domain.Librarian;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibrarianRepo extends JpaRepository<Librarian, Long> {
}
