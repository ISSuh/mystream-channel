package mystream.channel.domain.stream.repository;

import mystream.channel.domain.stream.entity.Stream;

public interface StreamRepository {
  
  Stream saveEntity(Stream stream);

}
