package unid.hyodoring.service.user;

import unid.hyodoring.web.dto.MemberReqDTO;

public interface UserService {

    void login(MemberReqDTO.LoginDTO loginDTO);
}
