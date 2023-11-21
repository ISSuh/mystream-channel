package mystream.channel.entity;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Preconditions;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "channel_id")
  private Long id;

  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "channel_stream", nullable = false)
  private ChannelStream stream;

  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "channel_description", nullable = false)
  private ChannelDescription description;

  @OneToMany(mappedBy = "channel")
  private List<ChannelSubscriber> subscribers = new ArrayList<>();

  @OneToMany(mappedBy = "channel")
  private List<ChannelFollower> followers = new ArrayList<>();

  public Channel(ChannelStream stream, ChannelDescription description) {
    this(null, stream, description, null, null);
    this.stream = stream;
    this.description = description;
  }

  public Channel(Long id, ChannelStream stream, ChannelDescription description,
      List<ChannelSubscriber> subscribers, List<ChannelFollower> followers) {
    Preconditions.checkArgument(stream != null, "stream must be not null");
    Preconditions.checkArgument(description != null, "description must be not null");

    this.id = id;
    this.stream = stream;
    this.description = description;

    if (subscribers != null) {
      this.subscribers = subscribers;
    }

    if (followers != null) {
      this.followers = followers;
    }
  }

  public void addFollwer(ChannelFollower follower) {
    this.followers.add(follower);
  }

  public void addSubscriber(ChannelSubscriber follower) {
    this.subscribers.add(follower);
  }

  
}
