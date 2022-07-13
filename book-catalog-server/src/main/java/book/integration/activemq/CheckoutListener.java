package book.integration.activemq;

import book.dto.NotifyCheckoutBookDTO;
import book.exception.ResourceNotFoundException;
import book.service.BookService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CheckoutListener {
  @Autowired
  private BookService bookService;

  @JmsListener(destination = "CHECKOUT_BOOK")
  public void receiveMsgFromActiveMQ(String checkoutMsg) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    try{
      NotifyCheckoutBookDTO bookDTO = objectMapper.readValue(checkoutMsg, NotifyCheckoutBookDTO.class);
      bookService.updateCheckedOutBook(bookDTO.getIsbn(), bookDTO.getScanCode());
    }catch(IOException e){
      throw new RuntimeException(e);
    } catch (ResourceNotFoundException e) {
      throw new RuntimeException(e);
    }
  }
}
