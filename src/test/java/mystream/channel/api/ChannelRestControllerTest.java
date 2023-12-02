package mystream.channel.api;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import mystream.channel.domain.channel.dto.ChannelDescriptionDto;
import mystream.channel.domain.channel.dto.NewChannelDto;
import mystream.channel.domain.channel.entity.Channel;
import mystream.channel.domain.channel.entity.ChannelDescription;
import mystream.channel.domain.channel.repository.ChannelRepository;
import mystream.channel.domain.stream.entity.ChannelStream;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
public class ChannelRestControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ChannelRepository channelRepository;

  @Test
  @Order(1)
  @DisplayName("find channel test")
  public void findChannel() throws Exception {
    // given
    createDummy();

    for (Long i = 1L ; i <= 5 ; i++ ) {
      // when
      ResultActions result = mockMvc.perform(
        MockMvcRequestBuilders.get("/api/channel/v1/channel/" + i)
          .contentType(MediaType.APPLICATION_JSON)
          .accept(MediaType.APPLICATION_JSON));

      // then
      result.andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.handler().handlerType(ChannelRestController.class))
        .andExpect(MockMvcResultMatchers.handler().methodName("findChannel"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
        .andExpect(MockMvcResultMatchers.jsonPath("$.error").isEmpty())
        .andExpect(MockMvcResultMatchers.jsonPath("$.result.id").value(i));
    }
  }

  @Test
  @Order(2)
  @DisplayName("find channel fail test")
  public void findChannelFail() throws Exception {
    // given
    Long channelId = 100L;

    // when
    ResultActions result = mockMvc.perform(
      MockMvcRequestBuilders.get("/api/channel/v1/channel/" + channelId)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON));

    // then
    result.andDo(MockMvcResultHandlers.print())
      .andExpect(MockMvcResultMatchers.status().is4xxClientError())
      .andExpect(MockMvcResultMatchers.handler().handlerType(ChannelRestController.class))
      .andExpect(MockMvcResultMatchers.handler().methodName("findChannel"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
      .andExpect(MockMvcResultMatchers.jsonPath("$.error").exists())
      .andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(HttpStatus.NOT_FOUND.value()));
  }

  @Test
  @Order(3)
  @DisplayName("create channel test")
  public void createChannel() throws Exception {
    // given
    NewChannelDto dto = new NewChannelDto(100L, "test_100");
    ObjectMapper mapper = new ObjectMapper();
    String dtoJsonString = mapper.writeValueAsString(dto);

    // when
    ResultActions result = mockMvc.perform(
      MockMvcRequestBuilders.post("/api/channel/v1/channel/new")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .content(dtoJsonString));

    // then
    result.andDo(MockMvcResultHandlers.print())
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(MockMvcResultMatchers.handler().handlerType(ChannelRestController.class))
      .andExpect(MockMvcResultMatchers.handler().methodName("createChannel"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
      .andExpect(MockMvcResultMatchers.jsonPath("$.error").isEmpty())
      .andExpect(MockMvcResultMatchers.jsonPath("$.result.id").value(100L));
  }

  @Transactional(readOnly = true)
  @Test
  @Order(4)
  @DisplayName("change channel description title")
  public void changechannelDescrition() throws Exception {
    // given
    createDummy();
    Long channelId = 1L;

    ResultActions result = mockMvc.perform(
      MockMvcRequestBuilders.get("/api/channel/v1/channel/" + channelId)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON));


    result.andDo(MockMvcResultHandlers.print())
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(MockMvcResultMatchers.handler().handlerType(ChannelRestController.class))
      .andExpect(MockMvcResultMatchers.handler().methodName("findChannel"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
      .andExpect(MockMvcResultMatchers.jsonPath("$.error").isEmpty())
      .andExpect(MockMvcResultMatchers.jsonPath("$.result.id").value(1))
      .andExpect(MockMvcResultMatchers.jsonPath("$.result.description.title").value("test title"));

    // when
    String changeTitle = "change title";
    ChannelDescriptionDto dto = new ChannelDescriptionDto(changeTitle, null);
    ObjectMapper mapper = new ObjectMapper();
    String dtoJsonString = mapper.writeValueAsString(dto);

    result = mockMvc.perform(
      MockMvcRequestBuilders.patch("/api/channel/v1/channel/" + channelId + "/description")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .content(dtoJsonString));

    // then
    result.andDo(MockMvcResultHandlers.print())
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(MockMvcResultMatchers.handler().handlerType(ChannelRestController.class))
      .andExpect(MockMvcResultMatchers.handler().methodName("updateChannelDescription"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
      .andExpect(MockMvcResultMatchers.jsonPath("$.error").isEmpty());

    Optional<Channel> channel = channelRepository.findById(channelId);
    Assertions.assertThat(channel.isPresent()).isTrue();
    Assertions.assertThat(channel.get().getDescription().getTitle()).isEqualTo(changeTitle);
  }

  // @Transactional(readOnly = true)
  // @Test
  // @Order(5)
  // @DisplayName("change channel stream active status")
  // public void changechannelStreamActiveStatus() throws Exception {
  //   Long channelId = 1L;

  //   ResultActions result = mockMvc.perform(
  //     MockMvcRequestBuilders.get("/api/channel/v1/channel/" + channelId)
  //       .contentType(MediaType.APPLICATION_JSON)
  //       .accept(MediaType.APPLICATION_JSON));

  //   result.andDo(MockMvcResultHandlers.print())
  //     .andExpect(MockMvcResultMatchers.status().isOk())
  //     .andExpect(MockMvcResultMatchers.handler().handlerType(ChannelRestController.class))
  //     .andExpect(MockMvcResultMatchers.handler().methodName("findChannel"))
  //     .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
  //     .andExpect(MockMvcResultMatchers.jsonPath("$.error").isEmpty())
  //     .andExpect(MockMvcResultMatchers.jsonPath("$.result.id").value(1))
  //     .andExpect(MockMvcResultMatchers.jsonPath("$.result.stream.active").value(false));

  //   Long streamId = 0L;
  //   String streamUrl = "modifyUrl";
  //   StreamActiveDto dto = new StreamActiveDto(streamId, true, streamUrl);
  //   ObjectMapper mapper = new ObjectMapper();
  //   String dtoJsonString = mapper.writeValueAsString(dto);

  //   result = mockMvc.perform(
  //     MockMvcRequestBuilders.patch("/api/channel/v1/channel/" + channelId + "/stream")
  //       .contentType(MediaType.APPLICATION_JSON)
  //       .accept(MediaType.APPLICATION_JSON)
  //       .content(dtoJsonString));

  //   result.andDo(MockMvcResultHandlers.print())
  //     .andExpect(MockMvcResultMatchers.status().isOk())
  //     .andExpect(MockMvcResultMatchers.handler().handlerType(ChannelRestController.class))
  //     .andExpect(MockMvcResultMatchers.handler().methodName("updateStreamActiveStatus"))
  //     .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
  //     .andExpect(MockMvcResultMatchers.jsonPath("$.error").isEmpty());

  //   Optional<Channel> channel = channelRepository.findById(channelId);
  //   Assertions.assertThat(channel.isPresent()).isTrue();
    // Assertions.assertThat(channel.get().getStream().getStreamId()).isEqualTo(streamId);
    // Assertions.assertThat(channel.get().getStream().getActive()).isEqualTo(StreamActive.ON);
    // Assertions.assertThat(channel.get().getStream().getStreamUrl()).isEqualTo(streamUrl);
  // }

  private void createDummy() {
    for (Long i = 0L ; i < 5 ; i++ ) {
      Long id = i + 1;
      String username = "test_" + id;
      ChannelStream stream = new ChannelStream();
      ChannelDescription description = new ChannelDescription("test title", null);
      Channel channel = new Channel(id, username, stream, description);
      channelRepository.saveEntity(channel);
    }
  }
}
