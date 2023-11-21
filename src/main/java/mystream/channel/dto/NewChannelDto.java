package mystream.channel.dto;

import lombok.Data;

@Data
public class NewChannelDto {

  Long streamId;

  public NewChannelDto() {
  }

  public NewChannelDto(Long streamId) {
    this.streamId = streamId;
  }

}
