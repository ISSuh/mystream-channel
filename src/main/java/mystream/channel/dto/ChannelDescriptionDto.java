package mystream.channel.dto;

import lombok.Data;
import mystream.channel.entity.ChannelDescription;

@Data
public class ChannelDescriptionDto {

  private String title;
  private Byte[] bannerImage;

  public ChannelDescriptionDto() {
  }

  public ChannelDescriptionDto(String title, Byte[] bannerImage) {
    this.title = title;
    this.bannerImage = bannerImage;
  }

  public ChannelDescriptionDto(ChannelDescription channelDescription) {
    this.title = channelDescription.getTitle();
    this.bannerImage = channelDescription.getBannerImage();
  }

}
