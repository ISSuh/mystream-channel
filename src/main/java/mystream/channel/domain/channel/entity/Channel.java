package mystream.channel.domain.channel.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.base.Preconditions;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mystream.channel.domain.base.entity.ModifyTime;
import mystream.channel.domain.follow.entity.ChannelFollower;
import mystream.channel.domain.stream.entity.ChannelStream;
import mystream.channel.domain.subscribe.entity.ChannelSubscriber;

@Entity
@Table(name = "channels")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Channel extends ModifyTime {
  
  @Id
  @Column(name = "channel_id")
  private Long id;

  @Column(name = "username")
  private String username;

  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "channel_stream_id", nullable = false)
  private ChannelStream channelStream;

  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "channel_description_id", nullable = false)
  private ChannelDescription description;

  @OneToMany(mappedBy = "channel", orphanRemoval = true)
  private List<ChannelSubscriber> subscribers = new ArrayList<>();

  @OneToMany(mappedBy = "channel", orphanRemoval = true)
  private Set<ChannelFollower> channelFollowers = new HashSet<>();

  public Channel(Long id, String username, ChannelStream channelStream, ChannelDescription description) {
    this(id, username, channelStream, description, null, null);
  }

  public Channel(Long id, String username, ChannelStream channelStream, ChannelDescription description,
      List<ChannelSubscriber> subscribers, Set<ChannelFollower> channelFollowers) {
    Preconditions.checkArgument(channelStream != null, "stream must be not null");
    Preconditions.checkArgument(description != null, "description must be not null");

    this.id = id;
    this.channelStream = channelStream;
    this.description = description;

    if (subscribers != null) {
      this.subscribers = subscribers;
    }

    if (channelFollowers != null) {
      this.channelFollowers = channelFollowers;
    }
  }

  public void addFollwer(ChannelFollower follower) {
    this.channelFollowers.add(follower);
  }

  public void removeFollwer(ChannelFollower follower) {
    this.channelFollowers.remove(follower);
  }

  public void addSubscriber(ChannelSubscriber follower) {
    this.subscribers.add(follower);
  }

  public void updateUsername(String username) {
    this.username = username;
  }

  @Override
  public String toString() {
    return "Channel [id=" + id + ", channelStream=" + channelStream + ", description=" + description + "]";
  }

}
