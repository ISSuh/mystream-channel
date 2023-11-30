package mystream.channel.domain.channel.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import mystream.channel.domain.channel.entity.ChannelDescription;

@Data
@NoArgsConstructor
public class ChannelDescriptionDto {

  private String title;
  private Byte[] bannerImage;

  public ChannelDescriptionDto(String title, Byte[] bannerImage) {
    this.title = title;
    this.bannerImage = bannerImage;
  }

  public ChannelDescriptionDto(ChannelDescription channelDescription) {
    this.title = channelDescription.getTitle();
    this.bannerImage = channelDescription.getBannerImage();
  }

}
