package mystream.channel.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum StreamActive {
  ON,
  OFF;

  @JsonCreator
  public static StreamActive from(String s) {
      return StreamActive.valueOf(s.toUpperCase());
  }
}
