package mystream.channel.entity;

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

@Entity
@Table(name = "channels")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Channel extends ModifyTime {
  
  @Id
  @Column(name = "channel_id")
  private Long id;

  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "channel_stream", nullable = false)
  private ChannelStream stream;

  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "channel_description", nullable = false)
  private ChannelDescription description;

  @OneToMany(mappedBy = "channel", orphanRemoval = true)
  private List<ChannelSubscriber> subscribers = new ArrayList<>();

  @OneToMany(mappedBy = "channel", orphanRemoval = true)
  private Set<ChannelFollower> channelFollowers = new HashSet<>();

  public Channel(Long id, ChannelStream stream, ChannelDescription description) {
    this(id, stream, description, null, null);
  }

  public Channel(Long id, ChannelStream stream, ChannelDescription description,
      List<ChannelSubscriber> subscribers, Set<ChannelFollower> channelFollowers) {
    Preconditions.checkArgument(stream != null, "stream must be not null");
    Preconditions.checkArgument(description != null, "description must be not null");

    this.id = id;
    this.stream = stream;
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

  @Override
  public String toString() {
    return "Channel [id=" + id + ", stream=" + stream + ", description=" + description + "]";
  }

}
