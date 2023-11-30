package mystream.channel.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import mystream.channel.domain.stream.entity.Stream;
import mystream.channel.domain.stream.repository.StreamRepository;

public interface StreamRepositoryJPA extends JpaRepository<Stream, Long>, StreamRepository {
  
  default Stream saveEntity(Stream stream) {
    return save(stream);
  }

}
