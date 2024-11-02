package unid.hyodoring.web.controller;

import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import unid.hyodoring.api.ApiResponse;
import unid.hyodoring.api.code.status.ErrorStatus;
import unid.hyodoring.api.code.status.SuccessStatus;
import unid.hyodoring.api.exception.GeneralException;
import unid.hyodoring.domain.User;
import unid.hyodoring.repository.UserRepository;
import unid.hyodoring.web.dto.SurveyRequestDTO;
import unid.hyodoring.web.dto.SurveyResponseDTO;

@RestController
@RequiredArgsConstructor
@Transactional
public class SurveyController {

  private final UserRepository userRepository;

  @PostMapping("/survey")
  ApiResponse<SurveyResponseDTO> apply(@RequestBody SurveyRequestDTO requestDTO) {
    SurveyResponseDTO surveyResponseDTO = new SurveyResponseDTO();

    List<Integer> answers = requestDTO.getAnswers();
    if (answers.size() != 7) {
      throw new GeneralException(ErrorStatus._BAD_REQUEST);
    }
    int sum = answers.stream().mapToInt(Integer::intValue).sum();
    surveyResponseDTO.setAnswerScore(sum);

    User user = userRepository.findById(requestDTO.getUser_id())
        .orElseThrow(() -> new GeneralException(
            ErrorStatus._BAD_REQUEST));

    user.setHyodoPower(sum);

    return ApiResponse.onSuccess(SuccessStatus._OK, surveyResponseDTO);
  }
}
