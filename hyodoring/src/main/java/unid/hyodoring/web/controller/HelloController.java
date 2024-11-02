package unid.hyodoring.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import unid.hyodoring.api.ApiResponse;
import unid.hyodoring.api.code.status.SuccessStatus;
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
