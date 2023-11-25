package mystream.channel.entity;

import java.time.LocalDateTime;

import com.google.common.base.Preconditions;

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
@Table(name = "channel_follower")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ChannelFollower {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "channel_follower_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "channel_id")
  private Channel channel;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "follower_id")
  private Follower follower;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "follow_at")
  private LocalDateTime followAt;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "unfollow_at")
  private LocalDateTime unFollowAt;

  public ChannelFollower(Channel channel, Follower follower) {
    this(null, channel, follower, null, null);
  }

  private ChannelFollower(Long id, Channel channel, Follower follower,
    LocalDateTime followAt, LocalDateTime unFollowAt) {
    Preconditions.checkArgument(channel != null, "channel must be no null");
    Preconditions.checkArgument(follower != null, "follower must be no null");

    this.id = id;
    this.channel = channel;
    this.follower = follower;
    this.followAt = followAt;
    this.unFollowAt = unFollowAt;
  }

  public static ChannelFollower create(Channel channel, Follower follower) {
    ChannelFollower channelFollower = new ChannelFollower(null, channel, follower, null, null);
    return channelFollower;
  }

  public void follow() {
    channel.addFollwer(this);
    this.followAt = LocalDateTime.now();
  }

  public void unfollow() {
    channel.removeFollwer(this);
    this.unFollowAt = LocalDateTime.now();
  }

}
