package mystream.channel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mystream.channel.entity.Stream;

public interface StreamRepository extends JpaRepository<Stream, Long> {
  
}
