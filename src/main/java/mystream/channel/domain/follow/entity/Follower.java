package mystream.channel.domain.follow.entity;

import com.google.common.base.Preconditions;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mystream.channel.domain.base.entity.ModifyTime;

@Entity
@Table(name = "follower")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Follower extends ModifyTime {
 
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "follower_id")
  private Long id;

  @Column(name = "user_id", nullable = false, unique = true)
  private Long userId;

  public Follower(Long userId) {
    this(null, userId);
  }

  public Follower(Long id, Long userId) {
    Preconditions.checkArgument(userId != null, "userId must be not null");

    this.id = id;
    this.userId = userId;
  }

}
