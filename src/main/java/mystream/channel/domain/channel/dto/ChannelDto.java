package mystream.channel.domain.channel.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import mystream.channel.domain.channel.entity.Channel;
import mystream.channel.domain.stream.dto.ChannelStreamDto;

@Data
@NoArgsConstructor
public class ChannelDto {

  private Long id;
  private ChannelStreamDto stream;
  private ChannelDescriptionDto description;

  public ChannelDto(Long id, ChannelStreamDto stream, ChannelDescriptionDto description) {
    this.id = id;
    this.stream = stream;
    this.description = description;
  }

  public ChannelDto(Channel channel) {
    this.id = channel.getId();
    this.stream = new ChannelStreamDto(channel.getChannelStream());
    this.description = new ChannelDescriptionDto(channel.getDescription());
  }

}
