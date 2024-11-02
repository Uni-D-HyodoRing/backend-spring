package unid.hyodoring.service.member;

import unid.hyodoring.web.dto.MemberReqDTO;

public interface UserService {

    void login(MemberReqDTO.LoginDTO loginDTO);
}
