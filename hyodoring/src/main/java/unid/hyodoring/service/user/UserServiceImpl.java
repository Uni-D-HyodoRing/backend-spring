package unid.hyodoring.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import unid.hyodoring.api.code.status.ErrorStatus;
import unid.hyodoring.api.exception.GeneralException;
import unid.hyodoring.domain.User;
import unid.hyodoring.repository.UserRepository;
import unid.hyodoring.web.dto.MemberReqDTO;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public void login(MemberReqDTO.LoginDTO loginDTO) {

        User user = userRepository.findByLoginId(loginDTO.getLoginId())
                .orElseThrow(() -> new GeneralException(ErrorStatus._NOT_FOUND));

        if (!loginDTO.getLoginPw().equals(user.getLoginPw())) {
            throw new GeneralException(ErrorStatus._UNAUTHORIZED);
        }
    }
}
