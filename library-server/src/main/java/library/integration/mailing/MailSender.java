package library.integration.mailing;

import org.springframework.stereotype.Service;

@Service
public class MailSender implements IMailSender{
  @Override
  public void sendMail(String mail, String msgBody, String subject) {
    System.out.println("Sending mail...................");
    System.out.println("To: " + mail );
    System.out.println("Subject: " + subject);
    System.out.println("Body: " + msgBody);
    System.out.println("Mail sent successfully ###############");
  }
}
