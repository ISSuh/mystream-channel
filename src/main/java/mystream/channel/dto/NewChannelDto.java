package mystream.channel.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NewChannelDto {

  Long id;

  public NewChannelDto(Long id) {
    this.id = id;
  }

}
