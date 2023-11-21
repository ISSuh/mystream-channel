package mystream.channel.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mystream.channel.dto.ChannelDto;
import mystream.channel.repository.ChannelRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChannelService {
  
  private final ChannelRepository channelRepository;

  public ChannelDto findChannelById(Long id) {
    return null;
  }

  public void changeChannelTitle(Long id, String title) {

  }

  public void changeChannelBannerImage(Long id, byte[] image) {

  }

}
