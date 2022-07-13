package book;

import book.repo.BookRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.Lifecycle;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.stereotype.Component;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@EnableJms
public class BookCatalogServerApplication {

  @Bean
  public ModelMapper modelMapper(){
    return new ModelMapper();
  }

  @Autowired
  private static ApplicationContext context;

  @Component static class ApplicationLifeCycle implements Lifecycle{
    @Autowired
    private BookRepo bookRepo;

    @Override
    public void start() {

    }

    @Override
    public void stop() {
      // clean mongo db
      bookRepo.deleteAll();
    }

    @Override
    public boolean isRunning() {
      return true;
    }
  }

  public static void main(String[] args) {
    SpringApplication.run(BookCatalogServerApplication.class, args);
  }

}
