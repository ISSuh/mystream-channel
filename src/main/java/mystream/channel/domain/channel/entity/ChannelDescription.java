package mystream.channel.domain.channel.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mystream.channel.domain.base.entity.ModifyTime;

@Entity
@Table(name = "channel_desciption")
@NoArgsConstructor()
@Getter
public class ChannelDescription extends ModifyTime {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "channel_description_id")
  private Long id;

  @Column(name = "titles", length = 255, nullable = false)
  private String title = "Welcome to my channel";

  @Lob
  @Column(name = "channel_banner_image")
  private Byte[] bannerImage;

  public ChannelDescription(String title, Byte[] bannerImage) {
    this(null, title, bannerImage);
  }

  public ChannelDescription(Long id, String title, Byte[] bannerImage) {
    this.id = id;
    this.title = title;
    this.bannerImage = bannerImage;
  }

  public void changeTitle(String title) {
    this.title = title;
  }

  public void changeBannderImage(Byte[] image) {
    this.bannerImage = image;
  }

  @Override
  public String toString() {
    return "ChannelDescription [id=" + id + ", title=" + title + "]";
  }

}
