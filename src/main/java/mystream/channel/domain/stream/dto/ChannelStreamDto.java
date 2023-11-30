package mystream.channel.domain.stream.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import mystream.channel.domain.stream.entity.ChannelStream;
import mystream.channel.domain.stream.entity.StreamActive;

@Data
@NoArgsConstructor
public class ChannelStreamDto {

  private boolean active;

  private int totalStreams;

  public ChannelStreamDto(boolean active, int totalStreams) {
    this.active = active;
    this.totalStreams = totalStreams;
  }

  public ChannelStreamDto(ChannelStream channelStream) {
    this.active =
      (channelStream.getActive() == StreamActive.ON) ? true : false;
    this.totalStreams = channelStream.getStreams().size();
  }
 
}
