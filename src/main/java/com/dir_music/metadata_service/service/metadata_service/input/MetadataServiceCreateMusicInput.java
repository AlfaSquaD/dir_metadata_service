package com.dir_music.metadata_service.service.metadata_service.input;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MetadataServiceCreateMusicInput {
    private String title;
    private String artist;
    private String album;
    private String genre;
    private String year;
    private String duration;
    private MultipartFile file;
}
