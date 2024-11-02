package unid.hyodoring.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class MwoHaeDto {
  @Getter
  @Setter
  @AllArgsConstructor
  @NoArgsConstructor
  public static class requestDto {
    private Long sender_user_id;
    private Long receiver_user_id;
  }
}
