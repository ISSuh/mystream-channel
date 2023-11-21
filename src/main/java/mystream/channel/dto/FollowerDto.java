package mystream.channel.dto;

import java.time.LocalDateTime;

import lombok.Data;
import mystream.channel.entity.ChannelFollower;

@Data
public class FollowerDto {
  
  private Long userId;
  private Long channelId;
  private LocalDateTime followAt;
  private LocalDateTime unFollowAt;
  
  public FollowerDto(Long userId, Long channelId, LocalDateTime followAt, LocalDateTime unFollowAt) {
    this.userId = userId;
    this.channelId = channelId;
    this.followAt = followAt;
    this.unFollowAt = unFollowAt;
  }

  public FollowerDto(ChannelFollower channelFollower) {
    this.channelId = channelFollower.getChannel().getId();
    this.userId = channelFollower.getFollower().getId();
    this.followAt = channelFollower.getFollowAt();
    this.unFollowAt = channelFollower.getUnFollowAt();
  }

}
