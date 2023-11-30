package mystream.channel.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import mystream.channel.api.utils.ApiResponse;
import mystream.channel.domain.channel.dto.ChannelDescriptionDto;
import mystream.channel.domain.channel.dto.ChannelDto;
import mystream.channel.domain.channel.dto.NewChannelDto;
import mystream.channel.domain.channel.service.ChannelService;
import mystream.channel.domain.follow.dto.FollowingDto;
import mystream.channel.domain.follow.service.FollowService;


@RestController
@RequestMapping("/api/channel/v1/channel")
@RequiredArgsConstructor
public class ChannelRestController {
  
  private final ChannelService channelService;
  private final FollowService followerService;

  @GetMapping("/{id}")
  public ApiResponse.ApiResult<ChannelDto> findChannel(
    @PathVariable Long id) {
    ChannelDto channel = channelService.find(id);
    return ApiResponse.success(channel);
  }

  @PostMapping("/new")
  public ApiResponse.ApiResult<ChannelDto> createChannel(
    @RequestBody NewChannelDto newChannelDto) {
    // create channel
    ChannelDto channel = channelService.create(newChannelDto);
    return ApiResponse.success(channel);
  }

  @PatchMapping("/{id}/description")
  public ApiResponse.ApiResult<ChannelDto> updateChannelDescription(
    @PathVariable Long id,
    @RequestBody ChannelDescriptionDto channelDescriptionDto) {
    channelService.updateChannelDescriton(id, channelDescriptionDto);
    return ApiResponse.success(null);
  }

  @PutMapping("/{id}/follow")
  public ApiResponse.ApiResult<?> follow(
    @PathVariable String id,
    @RequestBody FollowingDto followingDto) {
    followerService.followChannel(followingDto);
    return ApiResponse.success(null);
  }

  @PutMapping("/{id}/unfollow")
  public ApiResponse.ApiResult<?> unfollow(
    @PathVariable String id,
    @RequestBody FollowingDto followingDto) {
    followerService.unfollowChannel(followingDto);
    return ApiResponse.success(null);
  }

}
