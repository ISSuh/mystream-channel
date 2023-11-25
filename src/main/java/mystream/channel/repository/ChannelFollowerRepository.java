package mystream.channel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mystream.channel.entity.ChannelFollower;

public interface ChannelFollowerRepository extends JpaRepository<ChannelFollower, Long>, ChannelFollowerRepositoryCustom {

}
