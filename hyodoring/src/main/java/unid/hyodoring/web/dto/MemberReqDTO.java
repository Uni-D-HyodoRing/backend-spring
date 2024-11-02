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

    @Getter
    @RequiredArgsConstructor
    public static class registerDTO {
        private final String loginId;
        private final String loginPw;
        private final String name;
        private final String birth;
        private final String myRole;
    }
}
