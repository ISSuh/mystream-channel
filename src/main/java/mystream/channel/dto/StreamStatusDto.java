package mystream.channel.dto;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StreamStatusDto {
  
  private Long id;

  private boolean active;

  private String url;

  private LocalDateTime streamActiveAt;

  private LocalDateTime streamDeactiveAt;

  public StreamStatusDto(Long id, boolean active, String url, LocalDateTime streamActiveAt,
      LocalDateTime streamDeactiveAt) {
    this.id = id;
    this.active = active;
    this.url = url;
    this.streamActiveAt = streamActiveAt;
    this.streamDeactiveAt = streamDeactiveAt;
  }

}
