package unid.hyodoring.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import unid.hyodoring.api.ApiResponse;
import unid.hyodoring.api.code.status.SuccessStatus;
import unid.hyodoring.service.user.HelloService;
import unid.hyodoring.web.dto.HelloReqDTO;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class HelloController {

    private final HelloService helloService;

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
}
