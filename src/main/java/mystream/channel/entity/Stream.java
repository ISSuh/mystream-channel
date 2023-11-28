package mystream.channel.entity;

import java.time.LocalDateTime;

import com.google.common.base.Preconditions;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "stream")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Stream extends ModifyTime {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "stream_id")
  private Long id;

  @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "channel_stream_id", nullable = false)
  private ChannelStream channelStream;

  @Column(name = "stream_url", nullable = false)
  private String streamUrl;
  
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "active_at")
  private LocalDateTime activeAt;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "deactive_at")
  private LocalDateTime deactiveAt;

  public Stream(ChannelStream channelStream, String streamUrl) {
    this(null, channelStream, streamUrl, null, null);
  }

  public Stream(Long id, ChannelStream channelStream, String streamUrl, LocalDateTime startAt, LocalDateTime endAt) {
    Preconditions.checkArgument(channelStream != null, "channelStream must be not null");
    Preconditions.checkArgument(streamUrl != null, "streamUrl must be not null");

    this.id = id;
    this.channelStream = channelStream;
    this.streamUrl = streamUrl;
    this.activeAt = startAt;
    this.deactiveAt = endAt;
  }

  public void active(LocalDateTime activeAt) {
    this.activeAt = activeAt;
    this.channelStream.updateActiveStatus(StreamActive.ON);
  }

  public void deactive(LocalDateTime deactiveAt) {
    this.deactiveAt = deactiveAt;
    this.channelStream.updateActiveStatus(StreamActive.OFF);
  }

  @Override
  public String toString() {
    return "Stream [id=" + id + ", channelStream=" + channelStream + ", streamUrl=" + streamUrl + ", activeAt="
        + activeAt + ", deactiveAt=" + deactiveAt + "]";
  }

}
