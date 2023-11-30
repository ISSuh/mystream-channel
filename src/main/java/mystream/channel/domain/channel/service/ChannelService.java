package mystream.channel.domain.channel.service;

import mystream.channel.domain.channel.dto.ChannelDescriptionDto;
import mystream.channel.domain.channel.dto.ChannelDto;
import mystream.channel.domain.channel.dto.NewChannelDto;

public interface ChannelService {

  ChannelDto find(Long id);

  ChannelDto create(NewChannelDto newChannelDto);
  
  void updateChannelDescriton(Long id, ChannelDescriptionDto channelDescriptionDto);

}
