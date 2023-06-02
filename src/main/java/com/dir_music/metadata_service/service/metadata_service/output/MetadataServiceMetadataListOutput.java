package com.dir_music.metadata_service.service.metadata_service.output;

import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class MetadataServiceMetadataListOutput {
    private List<MetadataServiceMetadataOutput> results;


    @Data
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class MetadataServiceMetadataOutput {
        private Long id;
        private String title;
        private String artist;
        private String album;
        private String genre;
        private String year;
        private String track;
        private Long durationMillis;
        private Long sizeBytes;
        private boolean isListenable;
    }
}
