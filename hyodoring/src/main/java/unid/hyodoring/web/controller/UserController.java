package unid.hyodoring.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import unid.hyodoring.api.ApiResponse;
import unid.hyodoring.api.code.status.SuccessStatus;
import unid.hyodoring.service.user.UserService;
import unid.hyodoring.web.dto.MemberReqDTO;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    ApiResponse<String> login(@RequestBody MemberReqDTO.LoginDTO loginDTO) {
        userService.login(loginDTO);
        return ApiResponse.onSuccess(SuccessStatus._OK, "");
    }
}
