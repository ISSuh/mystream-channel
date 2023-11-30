package mystream.channel.services;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import mystream.channel.domain.channel.dto.ChannelDescriptionDto;
import mystream.channel.domain.channel.dto.ChannelDto;
import mystream.channel.domain.channel.dto.NewChannelDto;
import mystream.channel.domain.channel.service.ChannelService;
import mystream.channel.exceptions.channel.InvalidChannelCreateException;
import mystream.channel.infrastructure.persistence.ChannelRepositoryJPA;

@SpringBootTest()
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
public class ChannelServiceTest {

  @Autowired
  private ChannelService channelService;

  @PersistenceContext
  EntityManager entityManager;

  @Autowired
  ChannelRepositoryJPA channelRepositoryJPA;

  @Test
  @DisplayName("create channel")
  public void createTest() {
    // given
    Long id = 100L;
    String username = "test_" + id;
    NewChannelDto channel = new NewChannelDto(id, username);

    // when
    ChannelDto createdChannel = channelService.create(channel);

    // then
    Assertions.assertThat(createdChannel).isNotNull();
    Assertions.assertThat(createdChannel.getId()).isEqualTo(id);
  }

  @Test
  @DisplayName("create fail channel")
  @Transactional
  public void createFailAleadyExistIdTest() {
    // given
    Long id = 100L;
    String username = "test_" + id;
    NewChannelDto channel1 = new NewChannelDto(id, username);
    channelService.create(channel1);

    entityManager.flush();

    // when
    NewChannelDto channel2 = new NewChannelDto(id, username);

    // then
    Assertions.assertThatThrownBy(() -> channelService.create(channel2))
      .isInstanceOf(InvalidChannelCreateException.class);
 }

  @Test
  @DisplayName("find channel")
  public void findTest() {
    // given
    for (Long i = 0L ; i < 3 ; i++) {
      Long id = i + 100;
      String username = "test_" + id;
      NewChannelDto channel = new NewChannelDto(id, username);

      ChannelDto createdChannel = channelService.create(channel);
      Assertions.assertThat(createdChannel).isNotNull();
      Assertions.assertThat(createdChannel.getId()).isEqualTo(id);
    }

    // when
    List<ChannelDto> channels = new ArrayList<>();
    for (Long i = 0L ; i < 3 ; i++) {
      Long id = i + 100;
      channels.add(channelService.find(id));
    }

    // then
    for (int i = 0 ; i < 3 ; i++) {
      Long id = i + 100L;
      Assertions.assertThat(channels.get(i)).isNotNull();
      Assertions.assertThat(channels.get(i).getId()).isEqualTo(id);
    }
  }

  @Test
  @DisplayName("update desctiption")
  public void updateDeciptionTest() {
    // given
    Long id = 100L;
    String username = "test_" + id;
    NewChannelDto channel = new NewChannelDto(id, username);
    channelService.create(channel);

    // when
    String title = "test title";
    ChannelDescriptionDto description = new ChannelDescriptionDto();
    description.setTitle(title);

    channelService.updateChannelDescriton(id, description);

    // then
    ChannelDto find = channelService.find(id);
    Assertions.assertThat(find).isNotNull();
    Assertions.assertThat(find.getDescription().getTitle()).isEqualTo(title);
  }

}
