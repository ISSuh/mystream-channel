package mystream.channel.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import mystream.channel.dto.ChannelDescriptionDto;
import mystream.channel.dto.ChannelDto;
import mystream.channel.dto.NewChannelDto;
import mystream.channel.dto.StreamActiveDto;
import mystream.channel.service.ChannelService;
import mystream.channel.utils.ApiResponse;

@RestController
@RequestMapping("/api/channel/v1/channel")
@RequiredArgsConstructor
public class ChannelRestController {
  
  private final ChannelService channelService;

  @GetMapping("/{id}")
  public ApiResponse.ApiResult<ChannelDto> findChannel(
    @PathVariable Long id) {
    ChannelDto channel = channelService.findChannelById(id);
    return ApiResponse.success(channel);
  }

  @PostMapping("/new")
  public ApiResponse.ApiResult<ChannelDto> createChannel(
    @RequestBody NewChannelDto newChannelDto) {
    ChannelDto channel = channelService.createChannel(newChannelDto);
    return ApiResponse.success(channel);
  }

  @PatchMapping("/{id}/description")
  public ApiResponse.ApiResult<ChannelDto> updateChannelDescription(
    @PathVariable Long id,
    @RequestBody ChannelDescriptionDto channelDescriptionDto) {
    channelService.updateChannelDescriton(id, channelDescriptionDto);
    return ApiResponse.success(null);
  }

  @PatchMapping("/{id}/stream")
  public ApiResponse.ApiResult<ChannelDto> updateStreamActiveStatus(
    @PathVariable Long id,
    @RequestBody StreamActiveDto streamActiveDto) {
    channelService.updateStreamStatus(id, streamActiveDto);
    return ApiResponse.success(null);
  }

}
