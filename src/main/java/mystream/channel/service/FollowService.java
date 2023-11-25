package mystream.channel.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mystream.channel.dto.FollowingDto;
import mystream.channel.entity.Channel;
import mystream.channel.entity.ChannelFollower;
import mystream.channel.entity.Follower;
import mystream.channel.exceptions.InvalidChannelCreateException;
import mystream.channel.exceptions.InvalidFollowingException;
import mystream.channel.exceptions.NotFoundException;
import mystream.channel.repository.ChannelFollowerRepository;
import mystream.channel.repository.ChannelRepository;
import mystream.channel.repository.FollowerRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class FollowService {
  
  private final FollowerRepository followerRepository;
  private final ChannelFollowerRepository channelFollowerRepository;
  private final ChannelRepository channelRepository;

  public void createFollower(Long userid) {
    Optional<Follower> follower = followerRepository.findByUserId(userid);
    if (follower.isPresent()) {
      throw new InvalidChannelCreateException("alredy exsist follower");
    }

    followerRepository.save(new Follower(userid));
  }

  public void followChannel(FollowingDto followingDto) {
    if (followingDto.getChannelId() == followingDto.getUserId()) {
      throw new InvalidFollowingException("can not follow self channel");
    }
    
    Optional<ChannelFollower> channelFollower =
      channelFollowerRepository.findChannelFollowerByFollowerUserId(followingDto.getChannelId(), followingDto.getUserId());
    if (channelFollower.isPresent()) {
      throw new InvalidFollowingException("already followed channel.");
    }
    
    Channel channel =
      channelRepository.findChannelById(followingDto.getChannelId())
        .orElseThrow(() -> new NotFoundException("not found channel." + followingDto.getChannelId()));
    
    Follower follower =
      followerRepository.findByUserId(followingDto.getUserId())
        .orElseThrow(() -> new NotFoundException("not found follower." + followingDto.getUserId()));

    ChannelFollower newChannelFollower = ChannelFollower.create(channel, follower);
    newChannelFollower.follow();
  }

  public void unfollowChannel(FollowingDto followingDto) {
    ChannelFollower channelFollower =
      channelFollowerRepository.findChannelFollowerByFollowerUserId(followingDto.getChannelId(), followingDto.getUserId())
        .orElseThrow(() -> new NotFoundException("not found any followed between channel and user"));
    channelFollower.unfollow();
  }

}