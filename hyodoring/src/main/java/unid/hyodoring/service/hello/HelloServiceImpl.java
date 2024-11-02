package unid.hyodoring.service.hello;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import unid.hyodoring.api.code.status.ErrorStatus;
import unid.hyodoring.api.exception.GeneralException;
import unid.hyodoring.domain.Family;
import unid.hyodoring.domain.HelloPost;
import unid.hyodoring.domain.HelloPostImage;
import unid.hyodoring.domain.User;
import unid.hyodoring.repository.*;
import unid.hyodoring.web.dto.HelloReqDTO;
import unid.hyodoring.web.dto.HelloResDTO;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class HelloServiceImpl implements HelloService {

    private final UserRepository userRepository;
    private final HelloPostRepository helloPostRepository;
    private final HelloPostImageRepository helloPostImageRepository;
    private final CommentRepository commentRepository;

    @Override
    public String saveImage(MultipartFile image) {

        if (image.isEmpty()) {
            throw new GeneralException(ErrorStatus._BAD_REQUEST);
        }
        try {
            String UPLOAD_DIR = "src/main/resources/static";
            Path uploadPath = Path.of(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String originalFilename = image.getOriginalFilename();
            String extension = "";

            // 파일 확장자 추출
            int dotIndex = originalFilename.lastIndexOf('.');
            if (dotIndex > 0) {
                extension = originalFilename.substring(dotIndex);
            }

            String newFileName = UUID.randomUUID() + extension;
            Path path = uploadPath.resolve(newFileName);

            Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            return path.toString();

        } catch (IOException e) {
            throw new GeneralException(ErrorStatus._INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void sendHello(HelloReqDTO.HelloDTO helloDTO, List<String> images) {

        User sender = userRepository.findById(helloDTO.getSenderId())
                .orElseThrow(() -> new GeneralException(ErrorStatus._NOT_FOUND));

        User receiver = userRepository.findById(helloDTO.getReceiverId())
                .orElseThrow(() -> new GeneralException(ErrorStatus._NOT_FOUND));

        HelloPost helloPost = helloPostRepository.save(HelloPost.builder()
                .description(helloDTO.getText())
                .sender(sender)
                .receiver(receiver)
                .build());

        helloPost.setSender(sender);
        helloPost.setReceiver(receiver);
        helloPost.setFamily(sender.getFamily());

        HelloPost savedHelloPost = helloPostRepository.save(helloPost);

        images.forEach(image -> {
                    HelloPostImage helloPostImage = HelloPostImage.builder()
                            .imageName(image)
                            .helloPost(savedHelloPost)
                            .build();
                    helloPostImage = helloPostImageRepository.save(helloPostImage);
                    helloPostImage.setHelloPost(savedHelloPost);
                    savedHelloPost.addHelloPostImage(helloPostImage);
                });

    }

    @Override
    public HelloResDTO.HelloDetailDTO getHelloDetail(Long helloId) {

        HelloPost helloPost = helloPostRepository.findById(helloId)
                .orElseThrow(() -> new GeneralException(ErrorStatus._NOT_FOUND));

        List<String> images = helloPostImageRepository.findAllByHelloPost(helloPost).stream()
                .map(HelloPostImage::getImageName)
                .toList();

        return HelloResDTO.HelloDetailDTO.toDTO(helloPost, images);
    }

    @Override
    public List<HelloResDTO.HelloFullInfoDTO> getHelloList(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus._NOT_FOUND));

        Family family = user.getFamily();

        return helloPostRepository.findAllByFamily(family).stream()
                .map(helloPost -> {
                    List<String> images = helloPostImageRepository.findAllByHelloPost(helloPost).stream()
                            .map(HelloPostImage::getImageName)
                            .toList();
                    Long count = commentRepository.countByHelloPost(helloPost);
                    return HelloResDTO.HelloFullInfoDTO.toDTO(helloPost, images, count);
                })
                .toList();
    }
}
