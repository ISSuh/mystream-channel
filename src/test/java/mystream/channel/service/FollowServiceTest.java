package mystream.channel.service;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;
import mystream.channel.dto.ChannelDto;
import mystream.channel.dto.FollowingDto;
import mystream.channel.dto.NewChannelDto;
import mystream.channel.entity.Channel;
import mystream.channel.entity.ChannelFollower;
import mystream.channel.repository.ChannelFollowerRepository;
import mystream.channel.repository.ChannelRepository;

@SpringBootTest()
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
public class FollowServiceTest {

  @Autowired
  private ChannelService channelService;

  @Autowired
  private FollowService followService;

  @Autowired
  private ChannelFollowerRepository channelFollowerRepository;

  @Autowired
  private ChannelRepository channelRepository;


  @BeforeAll
  public void beforeAll() {
    for (Long i = 0L; i < 5 ; i++ ) {
      Long id = i + 1;
      NewChannelDto newChannelDto = new NewChannelDto(id);
      followService.createFollower(id);
      
      ChannelDto channel = channelService.createChannel(newChannelDto);
      log.info("[TEST] channel = {}", channel);
    }

    List<Channel> findAll = channelRepository.findAll();
    findAll.forEach(
      channel -> log.info("[TEST] finded channel = {}"));
  }

  @Test
  @Order(1)
  public void followTest() {
    Long userId = 1L;
    Long channelId = 3L;

    ChannelDto channel = channelService.findChannelById(channelId);
    log.info("[TEST] channel = {}", channel);

    FollowingDto followingDto = new FollowingDto(userId, channelId);
    followService.followChannel(followingDto);

    Optional<ChannelFollower> channelFollower =
      channelFollowerRepository.findChannelFollowerByFollowerUserId(channelId, userId);

    Assertions.assertThat(channelFollower.isPresent()).isTrue();
  }
}
