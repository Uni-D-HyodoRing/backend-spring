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
import unid.hyodoring.service.fcm.FCMService;
import unid.hyodoring.web.dto.MwoHaeDto;
import unid.hyodoring.web.dto.SurveyRequestDTO;

@RestController
@RequiredArgsConstructor
@Transactional
public class MwohaeController {

  private final MwohaeRequestRepository mwohaeRequestRepository;
  private final UserRepository userRepository;
  private final FCMService fcmService;

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

    fcmService.sendMessage(receiverUser.getId(), "뭐해 요청이 도착했습니다.",
        senderUser.getName() + "님이 " + receiverUser.getName() + " 님의 안부를 궁금해해요!");

    return ApiResponse.onSuccess(SuccessStatus._OK);
  }
}
