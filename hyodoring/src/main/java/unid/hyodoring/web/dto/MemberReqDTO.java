package unid.hyodoring.web.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class MemberReqDTO {

    @Getter
    @RequiredArgsConstructor
    public static class LoginDTO {
        private final String loginId;
        private final String loginPw;
    }
}
