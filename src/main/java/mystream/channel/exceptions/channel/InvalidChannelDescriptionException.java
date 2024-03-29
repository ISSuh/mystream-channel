package mystream.channel.exceptions.channel;

public class InvalidChannelDescriptionException extends RuntimeException {
  
  public InvalidChannelDescriptionException(String message) {
    super(message);
  }

  public InvalidChannelDescriptionException(String message, Throwable cause) {
    super(message, cause);
  }

}
