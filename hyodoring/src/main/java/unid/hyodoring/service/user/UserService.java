package unid.hyodoring.service.user;

import unid.hyodoring.domain.User;
import unid.hyodoring.web.dto.MemberReqDTO;

public interface UserService {

    User login(MemberReqDTO.LoginDTO loginDTO);

    User register(MemberReqDTO.registerDTO registerDTO);
}
