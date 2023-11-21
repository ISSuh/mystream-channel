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
@Table(name = "channel_follower")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ChannelFollower {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "channel_follower_id")
  private Long id;

  @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "channel_id")
  private Channel channel;

  @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "follower_id")
  private Follower follower;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "follow_at", nullable = false)
  private LocalDateTime followAt;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "unfollow_at", nullable = false)
  private LocalDateTime unFollowAt;

  private ChannelFollower(Long id, Channel channel, Follower follower,
    LocalDateTime followAt, LocalDateTime unFollowAt) {
    Preconditions.checkArgument(channel != null, "channel must be no null");
    Preconditions.checkArgument(follower != null, "follower must be no null");
    Preconditions.checkArgument(followAt != null, "followAt must be no null");
    Preconditions.checkArgument(unFollowAt != null, "unFollowAt must be no null");

    this.id = id;
    this.channel = channel;
    this.follower = follower;
    this.followAt = followAt;
    this.unFollowAt = unFollowAt;
  }

  public static ChannelFollower create(Channel channel, Follower follower, LocalDateTime followAt, LocalDateTime unFollowAt) {
    ChannelFollower channelFollower = new ChannelFollower(null, channel, follower, followAt, unFollowAt);
    channel.addFollwer(channelFollower);
    return channelFollower;
  }

}
