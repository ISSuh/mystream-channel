package mystream.channel.exceptions;

public class InvalidFollowingException extends RuntimeException {

  public InvalidFollowingException(String message) {
    super(message);
  }

  public InvalidFollowingException(String message, Throwable cause) {
    super(message, cause);
  }

}
