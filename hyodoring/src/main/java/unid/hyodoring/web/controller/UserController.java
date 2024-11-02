package unid.hyodoring.web.controller;

import jakarta.transaction.Transactional;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import unid.hyodoring.api.ApiResponse;
import unid.hyodoring.api.code.status.ErrorStatus;
import unid.hyodoring.api.code.status.SuccessStatus;
import unid.hyodoring.api.exception.GeneralException;
import unid.hyodoring.domain.Family;
import unid.hyodoring.domain.User;
import unid.hyodoring.repository.FamilyRepository;
import unid.hyodoring.repository.UserRepository;
import unid.hyodoring.service.user.UserService;
import unid.hyodoring.util.RandomStringGenerator;
import unid.hyodoring.web.dto.GroupMakeResponseDto;
import unid.hyodoring.web.dto.GroupRequestDto.MakeDTO;
import unid.hyodoring.web.dto.LoginResponseDTO;
import unid.hyodoring.web.dto.MemberReqDTO.LoginDTO;
import unid.hyodoring.web.dto.MemberReqDTO.registerDTO;

@RestController
@RequiredArgsConstructor
@Transactional
public class UserController {

  private final UserService userService;
  private final UserRepository userRepository;
  private final FamilyRepository familyRepository;

  @PostMapping("/login")
  ApiResponse<LoginResponseDTO> login(@RequestBody LoginDTO loginDTO) {
    User user = userService.login(loginDTO);
    LoginResponseDTO responseDTO = new LoginResponseDTO();
    responseDTO.setUser_id(user.getId());

    return ApiResponse.onSuccess(SuccessStatus._OK, responseDTO);
  }

  @PostMapping("/register")
  ApiResponse<LoginResponseDTO> register(@RequestBody registerDTO registerDTO) {
    User user = userService.register(registerDTO);
    LoginResponseDTO responseDTO = new LoginResponseDTO();
    responseDTO.setUser_id(user.getId());

    return ApiResponse.onSuccess(SuccessStatus._OK, responseDTO);
  }

  @PostMapping("/makegroup")
  ApiResponse<GroupMakeResponseDto> makeGroup(@RequestBody MakeDTO makeDTO) {
    User user = userRepository.findById(makeDTO.getUser_id())
        .orElseThrow(() -> new GeneralException(
            ErrorStatus._BAD_REQUEST));

    Family family = user.getFamily();
    if (family == null) {
      throw new GeneralException(ErrorStatus._BAD_REQUEST);
    }
    String joinCode = "12345";
    Boolean existJoinCode = true;
    while (existJoinCode) {
      joinCode = RandomStringGenerator.generateRandomString(5);
      existJoinCode = familyRepository.existsByJoinCode(joinCode);
    }

    Family family = Family.builder().joinCode(joinCode).build();
    familyRepository.save(family);
    user.setFamily(family);

    GroupMakeResponseDto groupMakeResponseDto = new GroupMakeResponseDto();
    groupMakeResponseDto.setGroup_id(family.getId());
    groupMakeResponseDto.setJoin_code(family.getJoinCode());

    return ApiResponse.onSuccess(SuccessStatus._OK, groupMakeResponseDto);
  }
}
