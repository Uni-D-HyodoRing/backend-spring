package unid.hyodoring.web.controller;

import jakarta.transaction.Transactional;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import unid.hyodoring.api.ApiResponse;
import unid.hyodoring.api.code.status.ErrorStatus;
import unid.hyodoring.api.code.status.SuccessStatus;
import unid.hyodoring.api.exception.GeneralException;
import unid.hyodoring.domain.HelloPost;
import unid.hyodoring.domain.HelloPostImage;
import unid.hyodoring.domain.MwohaeRequest;
import unid.hyodoring.domain.User;
import unid.hyodoring.domain.enums.Role;
import unid.hyodoring.repository.HelloPostImageRepository;
import unid.hyodoring.repository.HelloPostRepository;
import unid.hyodoring.repository.MwohaeRequestRepository;
import unid.hyodoring.repository.UserRepository;
import unid.hyodoring.web.dto.MainPageResponseDTO;
import unid.hyodoring.web.dto.MainPageResponseDTO.FamilyDTO;
import unid.hyodoring.web.dto.MainPageResponseDTO.ImageDTO;
import unid.hyodoring.web.dto.MainPageResponseDTO.MoHaeDto;
import unid.hyodoring.web.dto.MainPageResponseDTO.PostDTO;
import unid.hyodoring.web.dto.MainPageResponseDTO.ResultDTO;
import unid.hyodoring.web.dto.MwoHaeDto;

@RestController
@RequiredArgsConstructor
@Transactional
public class MainPageController {

  private final MwohaeRequestRepository mwohaeRequestRepository;
  private final UserRepository userRepository;
  private final HelloPostRepository helloPostRepository;
  private final HelloPostImageRepository helloPostImageRepository;

  @GetMapping("/mainpage/{userId}")
  ApiResponse<ResultDTO> index(@PathVariable int userId) {
    User user = userRepository.findById((long) userId).orElseThrow(() -> new GeneralException(
        ErrorStatus._BAD_REQUEST));

    List<MwohaeRequest> mwohaeRequests = mwohaeRequestRepository.findAllByReceiverId(user.getId());

    List<MoHaeDto> mwohaeList = mwohaeRequests.stream()
        .map(mwohaeRequest -> {
          MoHaeDto moHaeDTO = new MoHaeDto();
          moHaeDTO.setFromName(mwohaeRequest.getSender().getName());

          // DateTimeFormatter 정의
          DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
          String formattedDate = mwohaeRequest.getCreatedAt().format(formatter);

          moHaeDTO.setCreatedAt(formattedDate);
          return moHaeDTO;
        })
        .toList();

    List<User> families = userRepository.findByFamilyId(user.getFamily().getId());
    List<FamilyDTO> familyDTOList = families.stream()
        .filter((family) -> !Objects.equals(family.getId(), user.getId()))
        .map(family -> {
          FamilyDTO familyDTO = new FamilyDTO();
          familyDTO.setId(family.getId());
          familyDTO.setName(family.getName());
          familyDTO.setRole(family.getRole().toString());
          Optional<MwohaeRequest> lastRequest = mwohaeRequestRepository.findFirstBySenderIdOrderByCreatedAtDesc(
              family.getId());// senderId로 검색하고, createdAt을 기준으로 내림차순 정렬 후 첫 번째 데이터 가져오기
          if (lastRequest.isPresent()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDate = lastRequest.get().getCreatedAt().format(formatter);
            familyDTO.setTime(formattedDate);
          }
          return familyDTO;
        })
        .toList();

    List<HelloPost> posts = helloPostRepository.findAllByFamily(user.getFamily());
    List<PostDTO> postDTOList = posts.stream().filter((post) -> {
      if (user.getRole().equals(Role.SON) || user.getRole().equals(Role.DAUGHTER)) {
        return post.getSender().getRole().equals(Role.MOM) || post.getSender().getRole()
            .equals(Role.DAD);
      } else if (user.getRole().equals(Role.MOM) || user.getRole().equals(Role.DAD)) {
        return post.getSender().getRole().equals(Role.SON) || post.getSender().getRole()
            .equals(Role.DAUGHTER);
      }
      return false;
    }).map(post -> {
      PostDTO postDTO = new PostDTO();
      postDTO.setPostId(post.getId());
      postDTO.setWriterName(post.getSender().getName());
      postDTO.setContent(post.getDescription());
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
      String formattedDate = post.getCreatedAt().format(formatter);
      postDTO.setCreatedAt(formattedDate);

      List<HelloPostImage> helloPostImages = helloPostImageRepository.findAllByHelloPost(post);
      List<ImageDTO> imageDTOS = helloPostImages.stream().map(image -> {
        ImageDTO imageDTO = new ImageDTO();
        imageDTO.setImagePath(image.getImageName());
        return imageDTO;
      }).toList();

      postDTO.setImages(imageDTOS);
      return postDTO;
    }).toList();

    MainPageResponseDTO.ResultDTO resultDTO = new ResultDTO();

    resultDTO.setScore(user.getHyodoPower());
    resultDTO.setRecv_mohae_list(mwohaeList);
    resultDTO.setFamily_list(familyDTOList);
    resultDTO.setPost_list(postDTOList);

    return ApiResponse.onSuccess(SuccessStatus._OK, resultDTO);
  }
}
