package mystream.channel.infrastructure.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import mystream.channel.domain.channel.dto.ChannelDto;
import mystream.channel.domain.channel.entity.Channel;
import mystream.channel.domain.channel.repository.ChannelRepository;

public interface ChannelRepositoryJPA extends JpaRepository<Channel, Long>, ChannelRepository {
  
  @Query(
    "select c " +
    "from Channel c " +
    "where c.id = :id")
  Optional<Channel> findChannelById(Long id);

  @Query(
    "select new mystream.channel.domain.channel.dto.ChannelDto(c) " +
    "from Channel c " +
    "where c.id = :id")
  Optional<ChannelDto> findChannelDtoById(Long id);


  default Channel saveEntity(Channel channel) {
    return save(channel);
  }

  default Optional<Channel> findById(Long id) {
    return findChannelById(id);
  }

  default Optional<ChannelDto> findChannelDto(Long id) {
    return findChannelDtoById(id);
  }

}
