package library.exception;

import library.logging.ILogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
  @Autowired
  ILogger logger;

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
    List<String> details = new ArrayList<>();
    details.add(request.getDescription(false));
    ErrorDetails errorDetails = new ErrorDetails(LocalDate.now(), ex.getMessage(), details );
    logger.logError(errorDetails.toString(), ex);
    return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(CustomErrorException.class)
  public ResponseEntity<?> customExceptionHandler(CustomErrorException ex, WebRequest request) {
    List<String> details = new ArrayList<>();
    details.add(request.getDescription(false));
    ErrorDetails errorDetails = new ErrorDetails(LocalDate.now(), ex.getMessage(), details);
    if(ex.getStatus() == null){
      ex.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    logger.logError(errorDetails.toString(), ex);
    return new ResponseEntity<>(errorDetails,ex.getStatus());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> globalExceptionHandler(Exception ex, WebRequest request) {
    List<String> details = new ArrayList<>();
    details.add(request.getDescription(false));
    ErrorDetails errorDetails = new ErrorDetails(LocalDate.now(), ex.getMessage(), details);
    logger.logError(errorDetails.toString(), ex);
    return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    List<String> details = new ArrayList<>();
    for(ObjectError error : ex.getBindingResult().getAllErrors()) {
      details.add(error.getDefaultMessage());
    }
    ErrorDetails errorDetails = new ErrorDetails(LocalDate.now(), "Validation Failed", details);
    logger.logError(errorDetails.toString(), ex);
    return new ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST);
  }
}
