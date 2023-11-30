package mystream.channel.services;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mystream.channel.domain.channel.entity.Channel;
import mystream.channel.domain.channel.repository.ChannelRepository;
import mystream.channel.domain.follow.dto.FollowingDto;
import mystream.channel.domain.follow.entity.ChannelFollower;
import mystream.channel.domain.follow.entity.Follower;
import mystream.channel.domain.follow.repository.ChannelFollowerQuery;
import mystream.channel.domain.follow.repository.ChannelFollowerRepository;
import mystream.channel.domain.follow.repository.FollowerRepository;
import mystream.channel.domain.follow.service.FollowService;
import mystream.channel.exceptions.channel.InvalidChannelCreateException;
import mystream.channel.exceptions.common.NotFoundException;
import mystream.channel.exceptions.follow.InvalidFollowingException;

@Service
@RequiredArgsConstructor
@Transactional
public class FollowServiceImpl implements FollowService {
  
  private final FollowerRepository followerRepository;
  private final ChannelFollowerRepository channelFollowerRepository;
  private final ChannelRepository channelRepository;
  private final ChannelFollowerQuery channelFollowerQuery;

  public void createFollower(Long userid) {
    Optional<Follower> follower = followerRepository.findByUserId(userid);
    if (follower.isPresent()) {
      throw new InvalidChannelCreateException("alredy exsist follower");
    }

    followerRepository.saveEntity(new Follower(userid));
  }

  public void followChannel(FollowingDto followingDto) {
    if (followingDto.getChannelId() == followingDto.getUserId()) {
      throw new InvalidFollowingException("can not follow self channel");
    }
    
    Optional<ChannelFollower> channelFollower =
      channelFollowerQuery.findMatchChannelFollower(followingDto.getChannelId(), followingDto.getUserId());
    if (channelFollower.isPresent()) {
      throw new InvalidFollowingException("already followed channel.");
    }
    
    Channel channel =
      channelRepository.findById(followingDto.getChannelId())
        .orElseThrow(() -> new NotFoundException("not found channel." + followingDto.getChannelId()));
    
    Follower follower =
      followerRepository.findByUserId(followingDto.getUserId())
        .orElseThrow(() -> new NotFoundException("not found follower." + followingDto.getUserId()));

    ChannelFollower newChannelFollower = ChannelFollower.create(channel, follower);
    ChannelFollower savedChannelFollower = channelFollowerRepository.saveEntity(newChannelFollower);
    savedChannelFollower.follow();
  }

  public void unfollowChannel(FollowingDto followingDto) {
    ChannelFollower channelFollower =
      channelFollowerQuery.findMatchChannelFollower(followingDto.getChannelId(), followingDto.getUserId())
        .orElseThrow(() -> new NotFoundException("not found any followed between channel and user"));
    
    channelFollower.unfollow();
    channelFollowerRepository.deleteEntity(channelFollower.getId());
  }

}