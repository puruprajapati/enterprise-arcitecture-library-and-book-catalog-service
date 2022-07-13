package library.logging;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Logger implements ILogger {
  org.slf4j.Logger logger = LoggerFactory.getLogger(Logger.class);

  @Override
  public void logMessage(String logMessage) {
    logger.info(logMessage);
  }

  @Override
  public void logError(String errMessage, Exception ex) {
    logger.error(errMessage, ex);
  }
}
