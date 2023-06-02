package com.dir_music.metadata_service.client.song_streaming_client.models;


import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MusicAvailableResponseModel {
    List<MusicAvailableModel> musicAvailableModelList;


    @Data
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class MusicAvailableModel {
        private Long id;
        private Boolean isAvailable;
    }
}
