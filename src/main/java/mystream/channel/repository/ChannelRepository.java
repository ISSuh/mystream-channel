package mystream.channel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mystream.channel.entity.Channel;

public interface ChannelRepository extends JpaRepository<Channel, Long> {
  
}
