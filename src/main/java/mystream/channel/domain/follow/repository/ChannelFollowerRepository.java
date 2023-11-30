package mystream.channel.domain.follow.repository;

import mystream.channel.domain.follow.entity.ChannelFollower;

public interface ChannelFollowerRepository {
  
  ChannelFollower saveEntity(ChannelFollower channelFollower);

  void deleteEntity(Long id);

}
