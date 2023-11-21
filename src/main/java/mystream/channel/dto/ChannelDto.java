package mystream.channel.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import mystream.channel.entity.ChannelFollower;
import mystream.channel.entity.ChannelSubscriber;

@Data
public class ChannelDto {

  private Long id;
  private ChannelStreamDto stream;
  private ChannelDescriptionDto description;
  private List<ChannelSubscriber> subscribers = new ArrayList<>();
  private List<ChannelFollower> followers = new ArrayList<>();




}
