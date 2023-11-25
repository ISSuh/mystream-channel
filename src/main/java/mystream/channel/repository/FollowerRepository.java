package mystream.channel.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import mystream.channel.entity.Follower;

public interface FollowerRepository extends JpaRepository<Follower, Long> {
  
  Optional<Follower> findByUserId(Long userid);

}
