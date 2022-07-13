package library.integration.activemq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import library.dto.NotifyCheckoutBookDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class NotifyCheckout {
  @Autowired
  private JmsTemplate jmsTemplate;

  public void pushCheckoutMsgToActiveMQ(NotifyCheckoutBookDTO notifyCheckoutBook) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    String notifyMsg = objectMapper.writeValueAsString(notifyCheckoutBook);
    jmsTemplate.convertAndSend("CHECKOUT_BOOK", notifyMsg);
  }
}
