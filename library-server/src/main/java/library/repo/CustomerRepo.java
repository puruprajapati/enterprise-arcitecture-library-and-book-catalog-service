package library.repo;

import library.domain.Customer;
import library.dto.CustomerOutstandingFeeDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> {
  public Optional<Customer> findByCustomerNumber(String customerNumber);

  @Query(value = "select * from customers c where c.first_name ILIKE %:searchText% or c.email ILIKE %:searchText% or c.customer_number ILIKE %:searchText%", nativeQuery = true)
  List<Customer> searchCustomerByCustNoEmailName(@Param("searchText") String searchText);

  @Query(value = "select\n" +
    "       c.customer_number,\n" +
    "       max(concat(c.first_name, ' ', c.last_name)) as name,\n" +
    "       (sum(current_date - (ce.due_date)) * (:rate)) as outstanding_fee\n" +
    "from customers c\n" +
    "inner join checkout_entry ce on c.id = ce.customer_id\n" +
    "where ce.is_returned = false\n" +
    "and ce.due_date < current_date\n" +
    "and c.customer_number = :customerNumber " +
    "group by c.customer_number", nativeQuery = true)
  List<Object[]> getOutstandingAmountPerCustomer(@Param("customerNumber") String customerNumber, @Param("rate") double rate);

  @Query(value = "select\n" +
    "       c.customer_number,\n" +
    "       max(concat(c.first_name, ' ', c.last_name)) as name,\n" +
    "       (sum(current_date - (ce.due_date)) * (:rate)) as outstanding_fee\n" +
    "from customers c\n" +
    "inner join checkout_entry ce on c.id = ce.customer_id\n" +
    "where ce.is_returned = false\n" +
    "and ce.due_date < current_date\n" +
    "group by c.customer_number", nativeQuery = true)
  List<Object[]> getOutstandingAmountForAllCustomer(@Param("rate") double rate);
  @Query(value ="select\n" +
    "       ce.isbn,\n" +
    "       ce.scan_code,\n" +
    "       concat(c.first_name, ' ', c.last_name) as name,\n" +
    "       ce.borrowed_date,\n" +
    "       ce.return_date\n," +
    "       ce.due_date\n," +
    "       case when(ce.return_date is null) then 'BORROWED' else 'LATE RETURNED' END as status\n" +
    "from checkout_entry ce\n" +
    "inner join customers c on c.id = ce.customer_id\n" +
    "where ce.return_date is null OR ce.return_date > ce.due_date", nativeQuery = true)
  List<Object[]> getAllBorrowedAndLateReturnedBook();

  @Query(value ="select\n" +
    "    c.customer_number,\n" +
    "    concat(c.first_name, ', ', c.last_name) as name,\n" +
    "    c.email,\n" +
    " (select ce.isbn from checkout_entry limit 1) as test\n" +
//    "    coalesce((select to_json(array_agg(row_to_json(t))) from (select ce.isbn, ce.scan_code from checkout_entry ce where ce.customer_id = c.id and ce.return_date is  null)t ),'[]')::text as checked_out_book\n" +
    "from customers c\n" +
    "where c.customer_number = :customerNumber", nativeQuery = true)
  List<Object[]> getAllCustomerDetail(@Param("customerNumber") String customerNumber);

}
