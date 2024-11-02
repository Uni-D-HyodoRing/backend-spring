package unid.hyodoring.web.dto;

import lombok.Data;

@Data // 자동으로 getter, setter, toString, equals, hashCode 생성
public class LoginResponseDTO {

  private Long user_id;
}
