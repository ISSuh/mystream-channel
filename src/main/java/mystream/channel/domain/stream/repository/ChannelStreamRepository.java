package mystream.channel.domain.stream.repository;

import java.util.Optional;

import mystream.channel.domain.stream.entity.ChannelStream;

public interface ChannelStreamRepository {

  Optional<ChannelStream> findByChannelId(Long id); 
  
}
