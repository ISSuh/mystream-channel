package mystream.channel.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import mystream.channel.entity.ChannelStream;

public interface ChannelStreamRepository extends JpaRepository<ChannelStream, Long> {

  @Query(
    "select cs " +
    "from ChannelStream cs " +
    "join Channel c " +
      "on c.id = :id"
  )
  Optional<ChannelStream> findByChannelId(Long id); 
}
