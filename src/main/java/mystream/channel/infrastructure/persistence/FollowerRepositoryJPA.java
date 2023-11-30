package mystream.channel.infrastructure.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import mystream.channel.domain.follow.entity.Follower;
import mystream.channel.domain.follow.repository.FollowerRepository;

public interface FollowerRepositoryJPA extends JpaRepository<Follower, Long>, FollowerRepository {
  
  @Query(
    "select f " +
    "from Follower f " +
    "where f.userId = :userid")
  Optional<Follower> findFollowerByUserId(Long userid);

  default Follower saveEntity(Follower follower) {
    return save(follower);
  }

  default Optional<Follower> findByUserId(Long userid) {
    return findFollowerByUserId(userid);
  }

}
