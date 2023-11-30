package mystream.channel.domain.follow.repository;

import java.util.Optional;

import mystream.channel.domain.follow.entity.Follower;

public interface FollowerRepository {
  
  Follower saveEntity(Follower follower);

  Optional<Follower> findByUserId(Long userid);

}
