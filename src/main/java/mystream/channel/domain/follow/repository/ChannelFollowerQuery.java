package mystream.channel.domain.follow.repository;

import java.util.Optional;

import mystream.channel.domain.follow.entity.ChannelFollower;

public interface ChannelFollowerQuery {

  Optional<ChannelFollower> findMatchChannelFollower(Long channelId, Long userId);

}
