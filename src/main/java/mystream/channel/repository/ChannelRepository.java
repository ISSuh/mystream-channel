package mystream.channel.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import mystream.channel.dto.ChannelDto;
import mystream.channel.entity.Channel;

public interface ChannelRepository extends JpaRepository<Channel, Long> {
  @Query(
    "select c " +
    "from Channel c " +
    "where c.id = :id")
  Optional<Channel> findChannelById(Long id);

  @Query(
    "select new mystream.channel.dto.ChannelDto(c) " +
    "from Channel c " +
    "where c.id = :id")
  Optional<ChannelDto> findChannelDtoById(Long id);

}
