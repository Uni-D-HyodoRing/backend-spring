package unid.hyodoring.service.fcm;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import unid.hyodoring.api.code.status.ErrorStatus;
import unid.hyodoring.api.exception.GeneralException;
import unid.hyodoring.web.dto.FCMRequestDTO;

import java.util.HashMap;
import java.util.Map;

@Service
public class FCMServiceImpl implements FCMService {

    @Value("${spring.webflux.server}")
    private String serverUrl;

    private final WebClient webClient;

    public FCMServiceImpl() {
        ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(100 * 1024 * 1024))
                .build();
        this.webClient = WebClient.builder().exchangeStrategies(strategies).build();
    }

    @Override
    public Map sendMessage(Long member_id, String title, String body) {

        Map data;
        String url = serverUrl + "/api/notifications";
        FCMRequestDTO fcmRequestDTO = FCMRequestDTO.builder()
                .member_id(member_id)
                .title(title)
                .body(body)
                .build();
        try {
            data = webClient.post()
                    .uri(url)
                    .header("Content-Type", "application/json")
                    .bodyValue(fcmRequestDTO)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();
        } catch(Exception e) {
            throw new GeneralException(ErrorStatus._BAD_REQUEST);
        }
        return data;
    }

}
