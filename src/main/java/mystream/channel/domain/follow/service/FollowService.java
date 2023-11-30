package mystream.channel.domain.follow.service;

import mystream.channel.domain.follow.dto.FollowingDto;

public interface FollowService {
  
  void createFollower(Long userid);

  void followChannel(FollowingDto followingDto);

  void unfollowChannel(FollowingDto followingDto);

}