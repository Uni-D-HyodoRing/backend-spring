package unid.hyodoring.web.controller;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import unid.hyodoring.api.ApiResponse;
import unid.hyodoring.api.code.status.ErrorStatus;
import unid.hyodoring.api.code.status.SuccessStatus;
import unid.hyodoring.api.exception.GeneralException;
import unid.hyodoring.domain.MwohaeRequest;
import unid.hyodoring.domain.User;
import unid.hyodoring.repository.MwohaeRequestRepository;
import unid.hyodoring.repository.UserRepository;
import unid.hyodoring.web.dto.MwoHaeDto;
import unid.hyodoring.web.dto.SurveyRequestDTO;

@RestController
@RequiredArgsConstructor
@Transactional
public class MwohaeController {

  private final MwohaeRequestRepository mwohaeRequestRepository;
  private final UserRepository userRepository;

  @PostMapping("/mwohae")
  ApiResponse<Void> submit(@RequestBody MwoHaeDto.requestDto requestDto) {
    User senderUser = userRepository.findById(requestDto.getSender_user_id())
        .orElseThrow(() -> new GeneralException(
            ErrorStatus._BAD_REQUEST));
    User receiverUser = userRepository.findById(requestDto.getReceiver_user_id())
        .orElseThrow(() -> new GeneralException(
            ErrorStatus._BAD_REQUEST));
    mwohaeRequestRepository.save(MwohaeRequest.
        builder().
        sender(senderUser).
        receiver(receiverUser).
        isComplete(false)
        .build()
    );

    return ApiResponse.onSuccess(SuccessStatus._OK);
  }
}
