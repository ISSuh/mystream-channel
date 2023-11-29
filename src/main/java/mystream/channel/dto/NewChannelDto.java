package mystream.channel.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NewChannelDto {

  private Long id;
  
  private String username;

  public NewChannelDto(Long id, String username) {
    this.id = id;
    this.username = username;
  }

}
