package library.repo;

import library.domain.Customer;
import library.domain.ReservationEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepo extends JpaRepository<ReservationEntry, Long> {
  //  List<ReservationEntry> findByIsbnAndActiveTrue(String isbn);
  @Query(value = "select customer_id from reservation_entry where isbn = :isbn", nativeQuery = true)
  List<Long> findByIsbnAndActiveTrue(@Param("isbn") String isbn);

}
