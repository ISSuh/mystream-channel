package mystream.channel.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mystream.channel.domain.channel.entity.Channel;
import mystream.channel.domain.channel.repository.ChannelRepository;
import mystream.channel.domain.stream.dto.StreamStatusDto;
import mystream.channel.domain.stream.entity.ChannelStream;
import mystream.channel.domain.stream.entity.Stream;
import mystream.channel.domain.stream.repository.StreamRepository;
import mystream.channel.domain.stream.service.ChannelStreamService;
import mystream.channel.exceptions.common.NotFoundException;
import mystream.channel.exceptions.stream.InvalidChannelStreamActiveException;

@Service
@Transactional
@RequiredArgsConstructor
public class ChannelStreamServiceImpl implements ChannelStreamService {
  
  private final ChannelRepository channelRepository;
  private final StreamRepository streamRepository;

  public void updateStream(StreamStatusDto streamStatusDto) {
    Channel channel = channelRepository.findById(streamStatusDto.getId())
      .orElseThrow(() -> new NotFoundException("not found channel. " + streamStatusDto.getId()));

    ChannelStream channelStream = channel.getChannelStream();
    if (streamStatusDto.isActive()) {
      startNewStream(channelStream, streamStatusDto);
    } else {
      stopStream(channelStream, streamStatusDto);
    }
  }

  private void startNewStream(ChannelStream channelStream, StreamStatusDto streamStatusDto) {
    if (channelStream.isActive()) {
      throw new InvalidChannelStreamActiveException("stream already actived");
    }
    
    Stream lastStream = channelStream.lastStream();
    if ((lastStream != null) && 
        ((streamStatusDto.getStreamActiveAt() == null) ||
         (lastStream.getActiveAt().isAfter(streamStatusDto.getStreamActiveAt())))) {
      throw new InvalidChannelStreamActiveException("invalid stream active time");
    }

    Stream newStream = new Stream(channelStream, streamStatusDto.getUrl());
    Stream savedStream = streamRepository.saveEntity(newStream);
    channelStream.addStream(savedStream);

    savedStream.active(streamStatusDto.getStreamActiveAt());
  } 

  private void stopStream(ChannelStream channelStream, StreamStatusDto streamStatusDto) {
    if (!channelStream.isActive()) {
      throw new InvalidChannelStreamActiveException("stream already deactived");
    }

    if (streamStatusDto.getStreamDeactiveAt() == null) {
      throw new InvalidChannelStreamActiveException("invalid stream deactive time");
    }

    Stream lastStream = channelStream.lastStream();
    lastStream.deactive(streamStatusDto.getStreamDeactiveAt());
  }

}
