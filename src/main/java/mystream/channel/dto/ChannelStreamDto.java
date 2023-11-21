package mystream.channel.dto;

import lombok.Data;
import mystream.channel.entity.ChannelStream;
import mystream.channel.entity.StreamActive;

@Data
public class ChannelStreamDto {

  private Long streamId;
  private String streamUrl;
  private boolean active;

  public ChannelStreamDto() {
  }

  public ChannelStreamDto(Long streamId, String streamUrl, boolean active) {
    this.streamId = streamId;
    this.streamUrl = streamUrl;
    this.active = active;
  }

  public ChannelStreamDto(ChannelStream channelStream) {
    this.streamId = channelStream.getStreamId();
    this.streamUrl = channelStream.getStreamUrl();
    this.active =
      (channelStream.getActive() == StreamActive.ON) ? true : false;
  }
 
}
