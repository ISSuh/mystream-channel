package mystream.channel.domain.stream.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mystream.channel.domain.base.entity.ModifyTime;
import mystream.channel.exceptions.stream.InvalidChannelStreamStatusException;

@Entity
@Table(name = "channel_stream")
@NoArgsConstructor()
@Getter
public class ChannelStream extends ModifyTime {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "channel_stream_id")
  private Long id;

  @OneToMany(mappedBy = "channelStream")
  List<Stream> streams = new ArrayList<>();

  @Enumerated(EnumType.STRING)
  StreamActive active;

  public ChannelStream(Long id, List<Stream> streams, StreamActive active) {
    this.id = id;
    this.active = active;

    if (streams != null) {
      this.streams = streams;
    }
  }

  public boolean isActive() {
    return (this.active == StreamActive.ON);
  }

  public void updateActiveStatus(StreamActive active) {
    this.active = active;
  }

  public Stream currentStream() {
    if (!isActive()) {
      throw new InvalidChannelStreamStatusException("current stream is deactived");
    }

    if (this.streams.isEmpty()) {
      return null;
    }

    int lastIndex = this.streams.size() - 1;
    return this.streams.get(lastIndex);
  }

  public Stream lastStream() {
    if (this.streams.isEmpty()) {
      return null;
    }

    int lastIndex = this.streams.size() - 1;
    return this.streams.get(lastIndex);
  }

  public void addStream(Stream stream) {
    this.streams.add(stream);
  }

  @Override
  public String toString() {
    return "ChannelStream [id=" + id + ", active=" + active + "]";
  }
  
}
