package unid.hyodoring.service.hello;

import org.springframework.web.multipart.MultipartFile;
import unid.hyodoring.web.dto.HelloReqDTO;
import unid.hyodoring.web.dto.HelloResDTO;

import java.util.List;

public interface HelloService {

    void sendHello(HelloReqDTO.HelloDTO helloDTO, List<String> images);

    String saveImage(MultipartFile file);

    HelloResDTO.HelloDetailDTO getHelloDetail(Long helloId);

    List<HelloResDTO.HelloFullInfoDTO> getHelloList(Long userId);
}
