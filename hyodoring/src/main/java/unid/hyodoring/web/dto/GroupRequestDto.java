package unid.hyodoring.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import unid.hyodoring.domain.enums.Role;

@Getter
public class GroupRequestDto {

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class MakeDTO{
    private Long user_id;
  }

  @Getter
  @RequiredArgsConstructor
  public static class JoinDTO{
    private final Long user_id;
    private final String join_code;
    private final Role my_role;
  }
}
