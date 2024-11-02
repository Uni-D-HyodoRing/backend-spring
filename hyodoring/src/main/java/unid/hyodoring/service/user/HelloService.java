package unid.hyodoring.service.user;

import org.springframework.web.multipart.MultipartFile;
import unid.hyodoring.web.dto.HelloReqDTO;

import java.util.List;

public interface HelloService {

    void sendHello(HelloReqDTO.HelloDTO helloDTO, List<String> images);

    String saveImage(MultipartFile file);
}
