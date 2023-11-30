package mystream.channel.exceptions.stream;

public class InvalidChannelStreamActiveException extends RuntimeException {
  
  public InvalidChannelStreamActiveException(String message) {
    super(message);
  }

  public InvalidChannelStreamActiveException(String message, Throwable cause) {
    super(message, cause);
  }

}
