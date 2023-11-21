package mystream.channel.dto;

import lombok.Data;

@Data
public class StreamActiveDto {

  Long streamId;
  Boolean active;
  String streamUrl;

  public StreamActiveDto() {
  }

  public StreamActiveDto(Long streamId, Boolean active, String streamUrl) {
    this.streamId = streamId;
    this.active = active;
    this.streamUrl = streamUrl;
  }

}
