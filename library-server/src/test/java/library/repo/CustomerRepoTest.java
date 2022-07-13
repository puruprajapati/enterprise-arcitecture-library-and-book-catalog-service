package library.repo;

import library.domain.Customer;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerRepoTest {
  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private CustomerRepo customerRepo;

  @Test
  public void whenSearchByName_thenReturnCustomerOfThatName(){
    //given
    Customer customer = new Customer("EA001", "testUser@mail.com", "Harry", "Winks", "610114844", "1000 North 4th street", "Fairfield", "IOWA", 52557);
    entityManager.persist(customer);
    entityManager.flush();

    // when
    Customer found = (customerRepo.searchCustomerByCustNoEmailName("harry")).get(0);

    // then
    assertThat(customer.getCustomerNumber()).isEqualTo(found.getCustomerNumber());
  }
}
