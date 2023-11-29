package mystream.channel.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import mystream.channel.dto.NewChannelDto;
import mystream.channel.dto.StreamStatusDto;
import mystream.channel.entity.Channel;
import mystream.channel.entity.ChannelStream;
import mystream.channel.entity.Stream;
import mystream.channel.repository.ChannelRepository;

@SpringBootTest()
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
public class ChannelStreamServiceTest {

  @Autowired
  private ChannelService channelService;

  @Autowired
  private ChannelStreamService channelStreamService;

  @Autowired
  private ChannelRepository channelRepository;

  @BeforeAll
  @Transactional
  public void beforeAll() {
    for (Long i = 0L; i < 5 ; i++ ) {
      Long id = i + 1;
      String username = "test_" + id;
      NewChannelDto newChannelDto = new NewChannelDto(id, username);

      channelService.createChannel(newChannelDto);
    }
  }

  @Test
  @Transactional
  @DisplayName("stream active test")
  public void streamActiveTest() {
    // given
    Long id = 1L;
    LocalDateTime now = LocalDateTime.now();
    String streamUrl = "stream_" + id + "_" + now;

    StreamStatusDto streamStatusDto = new StreamStatusDto();
    streamStatusDto.setId(id);
    streamStatusDto.setUrl(streamUrl);
    streamStatusDto.setActive(true);
    streamStatusDto.setStreamActiveAt(now);

    // when
    channelStreamService.updateStream(streamStatusDto);

    // then
    Optional<Channel> channel = channelRepository.findById(id);
    Assertions.assertThat(channel.isPresent()).isTrue();

    ChannelStream channelStream = channel.get().getChannelStream();
    Assertions.assertThat(channelStream.isActive()).isTrue();
    Assertions.assertThat(channelStream.getStreams().size()).isEqualTo(1);

    Stream stream = channelStream.lastStream();
    log.info("[TEST] stream = {}", stream);
    Assertions.assertThat(stream.getStreamUrl()).isEqualTo(streamUrl);
    Assertions.assertThat(stream.getActiveAt()).isEqualTo(now);
    Assertions.assertThat(stream.getDeactiveAt()).isNull();
  }

  @Test
  @Transactional
  @DisplayName("stream deactive test")
  public void streamDeactiveTest() {
    // given
    Long id = 3L;
    LocalDateTime startAt = LocalDateTime.now();
    String streamUrl = "stream_" + id + "_" + startAt;

    StreamStatusDto active = new StreamStatusDto();
    active.setId(id);
    active.setUrl(streamUrl);
    active.setActive(true);
    active.setStreamActiveAt(startAt);

    channelStreamService.updateStream(active);

    // when
    LocalDateTime endAt = LocalDateTime.now();
    StreamStatusDto deactive = new StreamStatusDto();
    deactive.setId(id);
    deactive.setUrl(streamUrl);
    deactive.setActive(false);
    deactive.setStreamActiveAt(startAt);
    deactive.setStreamDeactiveAt(endAt);

    channelStreamService.updateStream(deactive);

    // then
    Optional<Channel> channel = channelRepository.findById(id);
    Assertions.assertThat(channel.isPresent()).isTrue();

    ChannelStream channelStream = channel.get().getChannelStream();
    Assertions.assertThat(channelStream.isActive()).isFalse();
    Assertions.assertThat(channelStream.getStreams().size()).isEqualTo(1);

    Stream stream = channelStream.lastStream();
    log.info("[TEST] stream = {}", stream);
    Assertions.assertThat(stream.getStreamUrl()).isEqualTo(streamUrl);
    Assertions.assertThat(stream.getActiveAt()).isEqualTo(startAt);
    Assertions.assertThat(stream.getDeactiveAt()).isEqualTo(endAt);
  }
}
