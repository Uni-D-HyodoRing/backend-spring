package unid.hyodoring.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import unid.hyodoring.api.ApiResponse;
import unid.hyodoring.api.code.status.ErrorStatus;
import unid.hyodoring.api.code.status.SuccessStatus;
import unid.hyodoring.api.exception.GeneralException;
import unid.hyodoring.domain.User;
import unid.hyodoring.repository.UserRepository;
import unid.hyodoring.service.fcm.FCMService;
import unid.hyodoring.service.hello.HelloService;
import unid.hyodoring.web.dto.HelloReqDTO;
import unid.hyodoring.web.dto.HelloResDTO;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class HelloController {

  private final HelloService helloService;
  private final FCMService fcmService;
  private final UserRepository userRepository;

  //@PostMapping("/hellos/messages")
  //public ApiResponse<Map> sendMessage() {
  //    Map response = fcmService.sendMessage(13L, "제목", "본문");
  //    return ApiResponse.onSuccess(SuccessStatus._OK, response);
  //}

  @PostMapping(value = "/hellos", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ApiResponse<String> sendHello(
      @RequestPart HelloReqDTO.HelloDTO helloDTO,
      @RequestPart(required = false) List<MultipartFile> files) {
    List<String> images = files.stream()
        .map(helloService::saveImage)
        .toList();
    helloService.sendHello(helloDTO, images);

    User user = userRepository.findById(helloDTO.getReceiverId())
        .orElseThrow(() -> new GeneralException(
            ErrorStatus._BAD_REQUEST));
      fcmService.sendMessage(user.getId(), "안부가 도착했어요~.",
          user.getName() + " 님으로부터 안부가 도착했습니다. 클릭해서 확인해보세요~");

    return ApiResponse.onSuccess(SuccessStatus._OK, "");
  }

  @GetMapping("/hellos/detail/{helloId}")
  public ApiResponse<HelloResDTO.HelloDetailDTO> getHelloDetail(
      @PathVariable Long helloId) {
    HelloResDTO.HelloDetailDTO helloDetailDTO = helloService.getHelloDetail(helloId);
    return ApiResponse.onSuccess(SuccessStatus._OK, helloDetailDTO);
  }

  @GetMapping("/hellos/records/users/{userId}")
  public ApiResponse<List<HelloResDTO.HelloFullInfoDTO>> getHelloList(
      @PathVariable Long userId) {
    List<HelloResDTO.HelloFullInfoDTO> helloDetailDTO = helloService.getHelloList(userId);
    return ApiResponse.onSuccess(SuccessStatus._OK, helloDetailDTO);
  }
}
