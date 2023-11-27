package mystream.channel.exceptions;

public class InvalidChannelStreamStatusException extends RuntimeException {

  public InvalidChannelStreamStatusException(String message) {
    super(message);
  }

  public InvalidChannelStreamStatusException(String message, Throwable cause) {
    super(message, cause);
  }

}
