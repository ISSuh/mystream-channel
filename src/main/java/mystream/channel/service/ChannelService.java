package mystream.channel.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mystream.channel.dto.ChannelDescriptionDto;
import mystream.channel.dto.ChannelDto;
import mystream.channel.dto.NewChannelDto;
import mystream.channel.entity.Channel;
import mystream.channel.entity.ChannelDescription;
import mystream.channel.entity.ChannelStream;
import mystream.channel.exceptions.InvalidChannelDescriptionException;
import mystream.channel.exceptions.NotFoundException;
import mystream.channel.repository.ChannelRepository;

@Service
@Transactional
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
    ChannelStream channelStream = new ChannelStream();
    ChannelDescription description = new ChannelDescription(INITIAL_TITLE);
    Channel channel = new Channel(id, channelStream, description);

    Channel savedChannel = channelRepository.save(channel);
    
    return new ChannelDto(savedChannel);
  }

  public void updateChannelDescriton(Long id, ChannelDescriptionDto channelDescriptionDto) {
    if (channelDescriptionDto.getTitle() == null || channelDescriptionDto.getTitle().length() > 255) {
      throw new InvalidChannelDescriptionException("invalid title");
    }

    Channel channel = channelRepository.findChannelById(id)
      .orElseThrow(() -> new NotFoundException("not found channel. " + id));
    
    ChannelDescription description = channel.getDescription();
    description.changeTitle(channelDescriptionDto.getTitle());
  }

}
