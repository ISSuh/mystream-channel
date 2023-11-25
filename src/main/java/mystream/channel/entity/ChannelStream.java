package mystream.channel.entity;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "channel_stream")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ChannelStream extends ModifyTime {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "channel_stream_id")
  private Long id;

  @Column(name = "stream_id", unique = true)
  private Long streamId;

  @Column(name = "stream_url")
  private String streamUrl;
  
  @Enumerated(EnumType.STRING)
  @ColumnDefault("'OFF'")
  private StreamActive active;

  public ChannelStream(Long streamId) {
    this(streamId, null);
  }

  public ChannelStream(Long streamId, String streamUrl) {
    this(null, streamId, streamUrl, StreamActive.OFF);
  }

  public ChannelStream(Long id, Long streamId, String streamUrl, StreamActive active) {
    this.id = id;
    this.streamId = streamId;
    this.streamUrl = streamUrl;
    this.active = active;
  }

  public boolean isStreamActive() {
    return (this.active == StreamActive.ON) ? true : false;
  }

  public void updateStreamActive(boolean active) {
    this.active = (active == true) ? StreamActive.ON : StreamActive.OFF;
  }

  public void updateStreamUrl(String streamUrl) {
    this.streamUrl = streamUrl;
  }

  @Override
  public String toString() {
    return "ChannelStream [id=" + id + ", streamId=" + streamId + ", streamUrl=" + streamUrl + ", active=" + active
        + "]";
  }
  
}
