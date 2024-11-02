package unid.hyodoring.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
public class HelloReqDTO {

    @Getter
    @Setter
    @AllArgsConstructor
    public static class HelloDTO {
        private Long senderId;
        private Long receiverId;
        private String text;
    }
}
