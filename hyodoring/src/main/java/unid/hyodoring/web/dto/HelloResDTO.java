package unid.hyodoring.web.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import unid.hyodoring.domain.HelloPost;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class HelloResDTO {

    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    @Builder(access = AccessLevel.PRIVATE)
    public static class HelloDetailDTO {

        private final LocalDateTime datetime;
        private final String senderName;
        private final String contents;
        private final List<String> images;

        public static HelloDetailDTO toDTO(HelloPost helloPost, List<String> images) {
            return HelloDetailDTO.builder()
                    .datetime(helloPost.getCreatedAt())
                    .senderName(helloPost.getSender().getName())
                    .contents(helloPost.getDescription())
                    .images(images)
                    .build();
        }
    }

    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    @Builder(access = AccessLevel.PRIVATE)
    public static class HelloFullInfoDTO {

        private final Long id;
        private final String description;
        private final String senderName;
        private final LocalDateTime createdAt;
        private final Long commentsCount;
        private final List<String> imagePaths;

        public static HelloFullInfoDTO toDTO(HelloPost helloPost, List<String> images, Long count) {
            return HelloFullInfoDTO.builder()
                    .id(helloPost.getId())
                    .description(helloPost.getDescription())
                    .senderName(helloPost.getSender().getName())
                    .createdAt(helloPost.getCreatedAt())
                    .commentsCount(count)
                    .imagePaths(images)
                    .build();
        }

    }
}
