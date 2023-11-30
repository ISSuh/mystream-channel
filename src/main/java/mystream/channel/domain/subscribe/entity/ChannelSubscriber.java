package mystream.channel.domain.subscribe.entity;

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
import mystream.channel.domain.base.entity.ModifyTime;
import mystream.channel.domain.channel.entity.Channel;

@Entity
@Table(name = "channel_subscriber")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ChannelSubscriber extends ModifyTime {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "channel_subscriber_id")
  private Long id;

  @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "channel_id")
  private Channel channel;

  @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "subscribe_id")
  private Subscriber subscriber;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "subscribe_at", nullable = false)
  private LocalDateTime subscribeAt;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "subscribe_update_at", nullable = true)
  private LocalDateTime subscribeUpdateAt;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "expire_at", nullable = false)
  private LocalDateTime expireAt;

  private ChannelSubscriber(Long id, Channel channel, Subscriber subscriber, LocalDateTime subscribeAt,
      LocalDateTime subscribeUpdateAt, LocalDateTime expireAt) {
    Preconditions.checkArgument(channel != null, "channel must be no null");
    Preconditions.checkArgument(subscriber != null, "subscriber must be no null");
    Preconditions.checkArgument(subscribeAt != null, "subscribeAt must be no null");
    Preconditions.checkArgument(expireAt != null, "expireAt must be no null");

    this.id = id;
    this.channel = channel;
    this.subscriber = subscriber;
    this.subscribeAt = subscribeAt;
    this.subscribeUpdateAt = subscribeUpdateAt;
    this.expireAt = expireAt;
  }

  public static ChannelSubscriber create(Channel channel, Subscriber subscriber, LocalDateTime subscribeAt, LocalDateTime expireAt) {
    ChannelSubscriber channelSubscriber = new ChannelSubscriber(null, channel, subscriber, subscribeAt, null, expireAt);
    channel.addSubscriber(channelSubscriber);
    return channelSubscriber;
  }

  public boolean isExpired() {
    return LocalDateTime.now().isAfter(this.expireAt);
  }

  public void updateSubscribe(LocalDateTime expireAt) {
    this.subscribeUpdateAt = LocalDateTime.now();
    this.expireAt = expireAt;
  }

}
