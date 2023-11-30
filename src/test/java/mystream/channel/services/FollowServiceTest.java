package mystream.channel.services;

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
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import mystream.channel.domain.channel.dto.ChannelDto;
import mystream.channel.domain.channel.dto.NewChannelDto;
import mystream.channel.domain.channel.entity.Channel;
import mystream.channel.domain.channel.repository.ChannelRepository;
import mystream.channel.domain.channel.service.ChannelService;
import mystream.channel.domain.follow.dto.FollowingDto;
import mystream.channel.domain.follow.entity.ChannelFollower;
import mystream.channel.domain.follow.repository.ChannelFollowerQuery;
import mystream.channel.domain.follow.repository.ChannelFollowerRepository;
import mystream.channel.domain.follow.service.FollowService;

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
  private ChannelFollowerQuery channelFollowerQuery;

  @Autowired
  private ChannelRepository channelRepository;

  @BeforeAll
  public void beforeAll() {
    for (Long i = 0L; i < 5 ; i++ ) {
      Long id = i + 1;
      String username = "test_" + id;
      NewChannelDto newChannelDto = new NewChannelDto(id, username);
      followService.createFollower(id);
      
      ChannelDto channel = channelService.create(newChannelDto);
      log.info("[TEST] channel = {}", channel);
    }
  }

  @Test
  @Order(1)
  public void followTest() {
    // given
    Long userId = 1L;
    Long channelId = 3L;

    // when
    FollowingDto followingDto = new FollowingDto(userId, channelId);
    followService.followChannel(followingDto);

    // then
    Optional<ChannelFollower> channelFollower =
      channelFollowerQuery.findMatchChannelFollower(channelId, userId);
    Assertions.assertThat(channelFollower.isPresent()).isTrue();
  }

  @Test
  @Order(2)
  @Transactional
  public void unfollowTest() {
    // given
    Long userId = 2L;
    Long channelId = 4L;

    FollowingDto followingDto = new FollowingDto(userId, channelId);
    followService.followChannel(followingDto);

    Optional<ChannelFollower> channelFollower =
      channelFollowerQuery.findMatchChannelFollower(channelId, userId);

    Channel channel1 = channelRepository.findById(channelId).get();

    Assertions.assertThat(channelFollower.isPresent()).isTrue();
    Assertions.assertThat(channel1.getChannelFollowers().size()).isEqualTo(1);

    // when
    followService.unfollowChannel(followingDto);

    // then
    channelFollower =
      channelFollowerQuery.findMatchChannelFollower(channelId, userId);

    Channel channel2 = channelRepository.findById(channelId).get();

    Assertions.assertThat(channelFollower.isPresent()).isFalse();
    Assertions.assertThat(channel2.getChannelFollowers().isEmpty()).isTrue();
  }
}
