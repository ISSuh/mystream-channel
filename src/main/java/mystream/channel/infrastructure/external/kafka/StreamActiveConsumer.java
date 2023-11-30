package mystream.channel.infrastructure.external.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mystream.channel.domain.stream.dto.StreamStatusDto;
import mystream.channel.domain.stream.service.ChannelStreamService;
import mystream.channel.exceptions.stream.InvalidChannelStreamActiveException;

@Service
@RequiredArgsConstructor
@Slf4j
public class StreamActiveConsumer {
  
  static private final String TOPIC_STREAM_ACTIVE = "stream-active";
  static private final String TOPIC_STREAM_DEACTIVE = "stream-deactive";
  static private final String GROUP_ID = "mystream-channel";

  private final ChannelStreamService channelStreamService;
  private final ObjectMapper mapper;

  @KafkaListener(topics = TOPIC_STREAM_ACTIVE, groupId = GROUP_ID)
  public void streamActiveEvent(@Headers MessageHeaders headers, @Payload String event) {
    StreamStatusDto status = null;
    try {
      status = mapper.readValue(event, StreamStatusDto.class);
    } catch (JsonProcessingException e) {
      log.error("[streamActiveEvent] e = {}", e.getMessage());
      throw new InvalidChannelStreamActiveException(e.getMessage());
    }

    log.info("[TEST][streamActiveEvent]header = {}, payload = {}", headers, status);

    channelStreamService.updateStream(status);
  }

  @KafkaListener(topics = TOPIC_STREAM_DEACTIVE, groupId = GROUP_ID)
  public void streamDeactiveEvent(@Headers MessageHeaders headers, @Payload String event) {
    StreamStatusDto status = null;
    try {
      status = mapper.readValue(event, StreamStatusDto.class);
    } catch (JsonProcessingException e) {
      log.error("[streamDeactiveEvent] e = {}", e.getMessage());
      throw new InvalidChannelStreamActiveException(e.getMessage());
    }

    log.info("[TEST][streamDeactiveEvent]header = {}, payload = {}", headers, event);
    channelStreamService.updateStream(status);
  }

}
