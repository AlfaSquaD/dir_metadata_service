package com.dir_music.metadata_service.controller.metadata_controller;


import com.dir_music.metadata_service.service.metadata_service.MetadataService;
import com.dir_music.metadata_service.service.metadata_service.input.MetadataServiceCreateMusicInput;
import com.dir_music.metadata_service.service.metadata_service.input.MetadataServiceSearchInput;
import com.dir_music.metadata_service.service.metadata_service.input.MetadataServiceSongIdInput;
import com.dir_music.metadata_service.service.metadata_service.output.MetadataServiceMetadataListOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController("MetadataController")
@RequestMapping("/metadata")
public class MetadataController {
    final private MetadataService metadataService;

    @Autowired
    public MetadataController(MetadataService metadataService) {
        this.metadataService = metadataService;
    }

    @GetMapping(path = "/search/{query}")
    public ResponseEntity<MetadataServiceMetadataListOutput> search(
            @RequestHeader("Authorization") String token,
            @PathVariable String query
    ) {

        final MetadataServiceMetadataListOutput metadataServiceSearchOutput = this.metadataService.searchMusic(
                MetadataServiceSearchInput.builder()
                        .searchQuery(query)
                        .build()
        );
        return ResponseEntity.ok(metadataServiceSearchOutput);

    }

    @PostMapping(path = "/create", consumes = "multipart/form-data", produces = "application/json")
    public ResponseEntity<String> createMusic(
            @RequestParam("file") MultipartFile file,
            @RequestParam("title") String title,
            @RequestParam("artist") String artist,
            @RequestParam("album") String album,
            @RequestParam("genre") String genre,
            @RequestParam("year") String year,
            @RequestParam("duration") String duration
    ) {

        final String output = this.metadataService.createMusic(
                MetadataServiceCreateMusicInput.builder()
                        .file(file)
                        .title(title)
                        .artist(artist)
                        .album(album)
                        .genre(genre)
                        .year(year)
                        .duration(duration)
                        .build()
        );
        return ResponseEntity.ok(output);
    }

    @PostMapping(path = "/get", consumes = "application/json", produces = "application/json")
    public ResponseEntity<MetadataServiceMetadataListOutput> getMusic(
            @RequestBody List<Long> ids
    ) {
        final MetadataServiceMetadataListOutput metadataServiceMetadataListOutput
                = this.metadataService.getListedMusic(new MetadataServiceSongIdInput(ids));
        return ResponseEntity.ok(metadataServiceMetadataListOutput);
    }

}
