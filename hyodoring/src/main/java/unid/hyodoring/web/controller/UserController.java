package unid.hyodoring.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import unid.hyodoring.api.ApiResponse;
import unid.hyodoring.api.code.status.SuccessStatus;
import unid.hyodoring.domain.User;
import unid.hyodoring.service.user.UserService;
import unid.hyodoring.web.dto.LoginResponseDTO;
import unid.hyodoring.web.dto.MemberReqDTO.LoginDTO;
import unid.hyodoring.web.dto.MemberReqDTO.registerDTO;

@RestController
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

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
}
