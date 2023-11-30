package mystream.channel.services;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mystream.channel.domain.channel.dto.ChannelDescriptionDto;
import mystream.channel.domain.channel.dto.ChannelDto;
import mystream.channel.domain.channel.dto.NewChannelDto;
import mystream.channel.domain.channel.entity.Channel;
import mystream.channel.domain.channel.entity.ChannelDescription;
import mystream.channel.domain.channel.repository.ChannelRepository;
import mystream.channel.domain.channel.service.ChannelService;
import mystream.channel.domain.stream.entity.ChannelStream;
import mystream.channel.exceptions.channel.InvalidChannelCreateException;
import mystream.channel.exceptions.channel.InvalidChannelDescriptionException;
import mystream.channel.exceptions.common.NotFoundException;

@Service
@Transactional
@RequiredArgsConstructor
public class ChannelServiceImpl implements ChannelService {

  private final ChannelRepository channelRepository;

  @Override
  public ChannelDto find(Long id) {
    ChannelDto channel = channelRepository.findChannelDto(id)
      .orElseThrow(() -> new NotFoundException("not found channel. " + id));
    return channel;
  }

  @Override
  public ChannelDto create(NewChannelDto newChannelDto) {
    final String INITIAL_TITLE = "Welcome to my channel";
    Long id = newChannelDto.getId();

    Optional<Channel> find = channelRepository.findById(id);
    if (find.isPresent()) {
      throw new InvalidChannelCreateException("alread exist channel id." + id);
    }

    ChannelStream channelStream = new ChannelStream();
    ChannelDescription description = new ChannelDescription(INITIAL_TITLE);
    Channel channel = new Channel(id, channelStream, description);

    Channel savedChannel = channelRepository.saveEntity(channel);
    return new ChannelDto(savedChannel);
  }

  @Override
  public void updateChannelDescriton(Long id, ChannelDescriptionDto channelDescriptionDto) {
    if (channelDescriptionDto.getTitle() == null || channelDescriptionDto.getTitle().length() > 255) {
      throw new InvalidChannelDescriptionException("invalid title");
    }

    Channel channel = channelRepository.findById(id)
      .orElseThrow(() -> new NotFoundException("not found channel. " + id));
    
    ChannelDescription description = channel.getDescription();
    description.changeTitle(channelDescriptionDto.getTitle());
  }

}
