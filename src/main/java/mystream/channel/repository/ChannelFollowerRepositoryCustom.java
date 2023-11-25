package mystream.channel.repository;

import java.util.Optional;

import mystream.channel.entity.ChannelFollower;

public interface ChannelFollowerRepositoryCustom {
  
  Optional<ChannelFollower> findChannelFollowerByChannelIdAndUserId(Long channelId, Long userId);

}
