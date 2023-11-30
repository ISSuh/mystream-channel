package mystream.channel.infrastructure.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import mystream.channel.domain.stream.entity.ChannelStream;
import mystream.channel.domain.stream.repository.ChannelStreamRepository;

public interface ChannelStreamRepositoryJPA extends JpaRepository<ChannelStream, Long>, ChannelStreamRepository {

  @Query(
    "select cs " +
    "from ChannelStream cs " +
    "join Channel c " +
      "on c.id = :id"
  )
  Optional<ChannelStream> findChannelStreamByChannelId(Long id); 

  default Optional<ChannelStream> findByChannelId(Long id) {
    return findChannelStreamByChannelId(id);
  }

}
