package mystream.channel.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import mystream.channel.api.utils.ApiResponse;
import mystream.channel.api.utils.ApiResponse.ApiResult;
import mystream.channel.exceptions.channel.InvalidChannelCreateException;
import mystream.channel.exceptions.channel.InvalidChannelDescriptionException;
import mystream.channel.exceptions.common.NotFoundException;
import mystream.channel.exceptions.stream.InvalidChannelStreamActiveException;
import mystream.channel.exceptions.stream.InvalidChannelStreamStatusException;

@ControllerAdvice
public class GeneralExceptionHandler {
  
  private ResponseEntity<ApiResponse.ApiResult<?>> response(Throwable throwable, HttpStatus status) {
    return response(throwable.getMessage(), status);
  }

  private ResponseEntity<ApiResponse.ApiResult<?>> response(String message, HttpStatus status) {
    HttpHeaders headers = new HttpHeaders();
    headers.add("content-type", MediaType.APPLICATION_JSON.toString());

    ApiResult<?> errorResponse = ApiResponse.error(message, status);
    return new ResponseEntity<>(errorResponse, headers, status);
  }

  @ExceptionHandler({
    NotFoundException.class,
    NoHandlerFoundException.class
  })
  public ResponseEntity<?> handleNotFound(Exception e) {
    return response(e, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler({
    IllegalArgumentException.class,
    IllegalStateException.class,
    InvalidChannelDescriptionException.class,
    InvalidChannelStreamActiveException.class,
    InvalidChannelCreateException.class,
    InvalidChannelCreateException.class,
    InvalidChannelStreamStatusException.class
  })
  public ResponseEntity<?> handleBadRequest(Exception e) {
    return response(e, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<?> handleMethodNottSupported(Exception e) {
    return response(e, HttpStatus.METHOD_NOT_ALLOWED);
  }

  @ExceptionHandler({
    Exception.class,
    RuntimeException.class
  })
  public ResponseEntity<?> handleOtherException(Exception e) {
    return response(e, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
