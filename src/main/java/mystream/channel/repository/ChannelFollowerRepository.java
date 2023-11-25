package mystream.channel.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import mystream.channel.entity.ChannelFollower;

public interface ChannelFollowerRepository extends JpaRepository<ChannelFollower, Long> {

  @Query(
    "select cf " +
    "from ChannelFollower cf " +
    "where cf.follower.userId = :userId and cf.channel.id = :channelId"
  )
  Optional<ChannelFollower> findChannelFollowerByFollowerUserId(Long channelId, Long userId);
}
