package mystream.channel.domain.follow.dto;

import lombok.Data;

@Data
public class FollowingDto {
  
  private Long userId;
  private Long channelId;

  public FollowingDto() {
  }

  public FollowingDto(Long userId, Long channelId) {
    this.userId = userId;
    this.channelId = channelId;
  }

}
