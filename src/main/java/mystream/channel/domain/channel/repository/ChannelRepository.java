package mystream.channel.domain.channel.repository;

import java.util.Optional;

import mystream.channel.domain.channel.dto.ChannelDto;
import mystream.channel.domain.channel.entity.Channel;

public interface ChannelRepository {

  Channel saveEntity(Channel channel);

  Optional<Channel> findById(Long id);

  Optional<ChannelDto> findChannelDto(Long id);

}
