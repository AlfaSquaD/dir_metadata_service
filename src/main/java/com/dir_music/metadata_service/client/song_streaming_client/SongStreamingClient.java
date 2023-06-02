package com.dir_music.metadata_service.client.song_streaming_client;


import com.dir_music.metadata_service.client.song_streaming_client.models.MusicAvailableResponseModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@FeignClient("dir-stream-song-service")
public interface SongStreamingClient {
    @PostMapping(value = "songs/isAvailable", consumes = "application/json", produces = "application/json")
    ResponseEntity<MusicAvailableResponseModel> getAvailableSongsById(@RequestBody List<Long> musicIds);

    @PostMapping(value = "songs/create/{artist}/{album}/{id}", consumes = "multipart/form-data", produces = "application/json")
    ResponseEntity<String> uploadMusic(@RequestPart("file") MultipartFile file,
                                      @PathVariable("artist") String artist,
                                      @PathVariable("album") String album,
                                      @PathVariable("id") Long id);

}
