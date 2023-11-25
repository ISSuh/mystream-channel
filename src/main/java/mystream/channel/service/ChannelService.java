package mystream.channel.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mystream.channel.dto.ChannelDescriptionDto;
import mystream.channel.dto.ChannelDto;
import mystream.channel.dto.NewChannelDto;
import mystream.channel.dto.StreamActiveDto;
import mystream.channel.entity.Channel;
import mystream.channel.entity.ChannelDescription;
import mystream.channel.entity.ChannelStream;
import mystream.channel.exceptions.InvalidChannelDescriptionException;
import mystream.channel.exceptions.InvalidChannelStreamActiveException;
import mystream.channel.exceptions.NotFoundException;
import mystream.channel.repository.ChannelRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ChannelService {

  private final ChannelRepository channelRepository;

  public ChannelDto findChannelById(Long id) {
    ChannelDto channel = channelRepository.findChannelDtoById(id)
      .orElseThrow(() -> new NotFoundException("not found channel. " + id));
    return channel;
  }

  public ChannelDto createChannel(NewChannelDto newChannelDto) {
    final String INITIAL_TITLE = "Welcome to my channel";
    Long id = newChannelDto.getId();
    ChannelStream stream = new ChannelStream(id);
    ChannelDescription description = new ChannelDescription(INITIAL_TITLE);
    Channel channel = new Channel(id, stream, description);

    Channel savedChannel = channelRepository.save(channel);
    return new ChannelDto(savedChannel);
  }

  @Transactional
  public void updateChannelDescriton(Long id, ChannelDescriptionDto channelDescriptionDto) {
    if (channelDescriptionDto.getTitle() == null || channelDescriptionDto.getTitle().length() > 255) {
      throw new InvalidChannelDescriptionException("invalid title");
    }

    Channel channel = channelRepository.findChannelById(id)
      .orElseThrow(() -> new NotFoundException("not found channel. " + id));
    
    ChannelDescription description = channel.getDescription();
    description.changeTitle(channelDescriptionDto.getTitle());
  }

  @Transactional
  public void updateStreamStatus(Long id, StreamActiveDto streamActiveDto) {
    Channel channel = channelRepository.findChannelById(id)
      .orElseThrow(() -> new NotFoundException("not found channel. " + id));
    

    ChannelStream stream = channel.getStream();
    if (stream.getStreamId() != streamActiveDto.getStreamId()) {
      throw new InvalidChannelStreamActiveException("invalid stream id");
    }
    
    if (stream.isStreamActive() == streamActiveDto.getActive()) {
      throw new InvalidChannelStreamActiveException("invalid stream active state");
    }

    stream.updateStreamActive(streamActiveDto.getActive());

    if (!streamActiveDto.getStreamUrl().isEmpty()) {
      stream.updateStreamUrl(streamActiveDto.getStreamUrl());
    }
  }

}
