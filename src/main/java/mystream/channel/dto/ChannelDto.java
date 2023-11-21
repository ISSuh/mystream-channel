package mystream.channel.dto;

import lombok.Data;
import mystream.channel.entity.Channel;

@Data
public class ChannelDto {

  private Long id;
  private ChannelStreamDto stream;
  private ChannelDescriptionDto description;
  
  public ChannelDto() {
  }

  public ChannelDto(Long id, ChannelStreamDto stream, ChannelDescriptionDto description) {
    this.id = id;
    this.stream = stream;
    this.description = description;
  }

  public ChannelDto(Channel channel) {
    this.id = channel.getId();
    this.stream = new ChannelStreamDto(channel.getStream());
    this.description = new ChannelDescriptionDto(channel.getDescription());
  }

}
