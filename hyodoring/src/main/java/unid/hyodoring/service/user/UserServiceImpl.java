package unid.hyodoring.service.user;

import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import unid.hyodoring.api.code.status.ErrorStatus;
import unid.hyodoring.api.exception.GeneralException;
import unid.hyodoring.domain.User;
import unid.hyodoring.domain.enums.Role;
import unid.hyodoring.repository.UserRepository;
import unid.hyodoring.web.dto.MemberReqDTO;
import unid.hyodoring.web.dto.MemberReqDTO.registerDTO;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User login(MemberReqDTO.LoginDTO loginDTO) {

        User user = userRepository.findByLoginId(loginDTO.getLoginId())
                .orElseThrow(() -> new GeneralException(ErrorStatus._NOT_FOUND));

        if (!loginDTO.getLoginPw().equals(user.getLoginPw())) {
            throw new GeneralException(ErrorStatus._UNAUTHORIZED);
        }

        return user;
    }

    @Override
    public User register(final registerDTO registerDTO) {

      User user = User.builder()
            .birth(LocalDate.parse(registerDTO.getBirth(), DateTimeFormatter.ofPattern("yyyy-MM-dd")))
            .name(registerDTO.getName())
            .loginId(registerDTO.getLoginId())
            .loginPw(registerDTO.getLoginPw())
            .role(Role.valueOf(registerDTO.getMyRole()))
            .build();
        return userRepository.save(user);
    }
}
