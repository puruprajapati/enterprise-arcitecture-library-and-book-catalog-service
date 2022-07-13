package library;

import library.config.ApplicationProperties;
import library.dto.CustomerOutstandingFeeDTO;
import library.repo.CustomerRepo;
import library.service.impl.CustomerServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.List;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationProperties.class)
@EnableScheduling
@EnableJms
@EnableJpaRepositories
public class LibraryServerApplication implements CommandLineRunner {
  @Autowired
  CustomerRepo customerRepo;

  @Bean
  public ModelMapper modelMapper(){
    return new ModelMapper();
  }

  public static void main(String[] args) {
    SpringApplication.run(LibraryServerApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
//    List<Object[]> test = customerRepo.getAllCustomerDetail("EA001");
//    System.out.println(test);
  }
}
