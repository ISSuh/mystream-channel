package mystream.channel.exceptions.channel;

public class InvalidChannelCreateException extends RuntimeException {

  public InvalidChannelCreateException(String message) {
    super(message);
  }

  public InvalidChannelCreateException(String message, Throwable cause) {
    super(message, cause);
  }

}
