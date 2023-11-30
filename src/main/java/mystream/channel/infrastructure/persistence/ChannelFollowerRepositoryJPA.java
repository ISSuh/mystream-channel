package mystream.channel.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import mystream.channel.domain.follow.entity.ChannelFollower;
import mystream.channel.domain.follow.repository.ChannelFollowerRepository;

public interface ChannelFollowerRepositoryJPA extends JpaRepository<ChannelFollower, Long>, ChannelFollowerRepository {

  default ChannelFollower saveEntity(ChannelFollower channelFollower) {
    return save(channelFollower);
  }

  default void deleteEntity(Long id) {
    deleteById(id); 
  }

}
