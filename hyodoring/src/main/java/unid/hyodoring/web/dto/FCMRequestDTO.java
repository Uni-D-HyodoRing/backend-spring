package unid.hyodoring.web.dto;

import lombok.*;

@Getter
@RequiredArgsConstructor
@Builder
public class FCMRequestDTO {
    private final Long member_id;
    private final String title;
    private final String body;
}
