package unid.hyodoring.web.dto;

import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class MainPageResponseDTO {

  @Getter
  @Setter
  public static class MoHaeDto {
    private String fromName;
    private String createdAt;
  }

  @Getter
  @Setter
  public static class FamilyDTO {
    private Long id;
    private String name;
    private String role;
    private String time;
  }

  @Getter
  @Setter
  public static class PostDTO {
    private Long postId;
    private String createdAt;
    private String writerName;
    private String content;
    private List<ImageDTO> images;
  }

  @Getter
  @Setter
  public static class ImageDTO {
    private String imagePath;
  }

  @Data
  public static class ResultDTO {
    private int score;
    private List<MoHaeDto> recv_mohae_list;
    private List<FamilyDTO> family_list;
    private List<PostDTO> post_list;
  }
}