package library.logging;

public interface ILogger {
  public void logMessage (String logMessage);
  public void logError(String errMessage, Exception ex);
}
