package mystream.channel.entity;

import org.checkerframework.checker.units.qual.s;
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

  @Column(name = "stream_id", nullable = false, unique = true)
  private Long streamId;

  @Column(name = "stream_url", length = 100, nullable = true)
  private String streamUrl;
  
  @Enumerated(EnumType.STRING)
  @ColumnDefault("OFF")
  private StreamActive active;

  public ChannelStream(String streamUrl) {
    this(null, streamUrl, StreamActive.OFF);
  }

  public ChannelStream(Long id, String streamUrl, StreamActive active) {
    this.id = id;
    this.streamUrl = streamUrl;
    this.active = active;
  }

}
