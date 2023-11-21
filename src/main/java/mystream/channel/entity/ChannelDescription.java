package mystream.channel.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "channel_desciption")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ChannelDescription {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "channel_description_id")
  private Long id;

  @Column(name = "titles", length = 255, nullable = true)
  private String title;

  @Lob
  @Column(name = "channel_banner_image", nullable = true)
  private Byte[] bannerImage;
}
