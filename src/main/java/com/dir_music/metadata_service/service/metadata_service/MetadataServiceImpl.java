package com.dir_music.metadata_service.service.metadata_service;

import com.dir_music.metadata_service.client.song_streaming_client.SongStreamingClient;
import com.dir_music.metadata_service.client.song_streaming_client.models.MusicAvailableResponseModel;
import com.dir_music.metadata_service.repository.metadata_repository.MetadataModel;
import com.dir_music.metadata_service.repository.metadata_repository.MetadataRepository;
import com.dir_music.metadata_service.service.metadata_service.input.MetadataServiceCreateMusicInput;
import com.dir_music.metadata_service.service.metadata_service.input.MetadataServiceSearchInput;
import com.dir_music.metadata_service.service.metadata_service.input.MetadataServiceSongIdInput;
import com.dir_music.metadata_service.service.metadata_service.output.MetadataServiceMetadataListOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class MetadataServiceImpl implements MetadataService {

    final private SongStreamingClient songStreamingClient;
    final private MetadataRepository metadataRepository;

    @Autowired
    public MetadataServiceImpl(SongStreamingClient songStreamingClient,
                               MetadataRepository metadataRepository) {
        this.songStreamingClient = songStreamingClient;
        this.metadataRepository = metadataRepository;
    }

    @Override
    public MetadataServiceMetadataListOutput searchMusic(MetadataServiceSearchInput searchInput) {
        final Optional<List<MetadataModel>> songs = metadataRepository.searchMusic(searchInput.getSearchQuery());

        if (songs.isEmpty()) {
            return MetadataServiceMetadataListOutput.builder()
                    .results(Collections.emptyList())
                    .build();
        }

        List<Long> songIds = songs.get().stream().map(MetadataModel::getId).toList();

        final ResponseEntity<MusicAvailableResponseModel> musicAvailableResponseModel =
                songStreamingClient.getAvailableSongsById(songIds);

        if (musicAvailableResponseModel.getStatusCode().is2xxSuccessful()) {
            final HashMap<Long, MetadataServiceMetadataListOutput.MetadataServiceMetadataOutput>
                    metadataServiceMetadataOutputHashMap = new HashMap<>();

            songs.get().forEach(
                    metadataModel -> {
                        final MetadataServiceMetadataListOutput.MetadataServiceMetadataOutput
                                metadataServiceMetadataOutput = MetadataServiceMetadataListOutput.MetadataServiceMetadataOutput.builder()
                                .id(metadataModel.getId())
                                .title(metadataModel.getTitle())
                                .artist(metadataModel.getArtist())
                                .album(metadataModel.getAlbum())
                                .genre(metadataModel.getGenre())
                                .durationMillis(metadataModel.getDurationMillis())
                                .year(metadataModel.getYear())
                                .sizeBytes(metadataModel.getSizeBytes())
                                .build();
                        metadataServiceMetadataOutputHashMap.put(metadataModel.getId(), metadataServiceMetadataOutput);
                    }
            );
            Objects.requireNonNull(musicAvailableResponseModel.getBody()).getMusicAvailableModelList().forEach(
                    musicAvailableModel -> {
                        metadataServiceMetadataOutputHashMap.get(musicAvailableModel.getId())
                                .setListenable(musicAvailableModel.getIsAvailable());
                    }
            );

            final ArrayList<MetadataServiceMetadataListOutput.MetadataServiceMetadataOutput>
                    metadataServiceMetadataOutputs = new ArrayList<>(metadataServiceMetadataOutputHashMap.values());

            return MetadataServiceMetadataListOutput.builder()
                    .results(metadataServiceMetadataOutputs)
                    .build();

        }

        throw new RuntimeException("Something went wrong");
    }

    @Override
    public String createMusic(MetadataServiceCreateMusicInput createMusicInput) {

        MetadataModel metadataModel = MetadataModel.builder()
                .title(createMusicInput.getTitle())
                .artist(createMusicInput.getArtist())
                .album(createMusicInput.getAlbum())
                .genre(createMusicInput.getGenre())
                .durationMillis(Long.parseLong(createMusicInput.getDuration()))
                .year(createMusicInput.getYear())
                .sizeBytes(createMusicInput.getFile().getSize())
                .id(0L)
                .build();

        metadataModel = metadataRepository.save(metadataModel);

        try {
            songStreamingClient.uploadMusic(createMusicInput.getFile(), createMusicInput.getArtist(), createMusicInput.getAlbum(), metadataModel.getId());
        } catch (Exception e) {
            metadataRepository.delete(metadataModel);
            throw new RuntimeException("Something went wrong");
        }
        return "Success";
    }

    @Override
    public MetadataServiceMetadataListOutput getListedMusic(MetadataServiceSongIdInput songIdInput) {
        final ArrayList<MetadataModel>
                songs = new ArrayList<>();

        songIdInput.getSongIds().forEach(
                aLong -> {
                    songs.add(metadataRepository.findById(aLong).orElseThrow());
                }
        );


        final ResponseEntity<MusicAvailableResponseModel> musicAvailableResponseModel =
                songStreamingClient.getAvailableSongsById(songIdInput.getSongIds());

        if (musicAvailableResponseModel.getStatusCode().is2xxSuccessful()) {
            final HashMap<Long, MetadataServiceMetadataListOutput.MetadataServiceMetadataOutput>
                    metadataServiceMetadataOutputHashMap = new HashMap<>();

            songs.forEach(
                    metadataModel -> {
                        final MetadataServiceMetadataListOutput.MetadataServiceMetadataOutput
                                metadataServiceMetadataOutput = MetadataServiceMetadataListOutput.MetadataServiceMetadataOutput.builder()
                                .id(metadataModel.getId())
                                .title(metadataModel.getTitle())
                                .artist(metadataModel.getArtist())
                                .album(metadataModel.getAlbum())
                                .genre(metadataModel.getGenre())
                                .durationMillis(metadataModel.getDurationMillis())
                                .year(metadataModel.getYear())
                                .sizeBytes(metadataModel.getSizeBytes())
                                .build();
                        metadataServiceMetadataOutputHashMap.put(metadataModel.getId(), metadataServiceMetadataOutput);
                    }
            );

            Objects.requireNonNull(musicAvailableResponseModel.getBody()).getMusicAvailableModelList().forEach(
                    musicAvailableModel -> {
                        metadataServiceMetadataOutputHashMap.get(musicAvailableModel.getId())
                                .setListenable(musicAvailableModel.getIsAvailable());
                    }
            );

            final ArrayList<MetadataServiceMetadataListOutput.MetadataServiceMetadataOutput>
                    metadataServiceMetadataOutputs = new ArrayList<>(metadataServiceMetadataOutputHashMap.values());

            return MetadataServiceMetadataListOutput.builder()
                    .results(metadataServiceMetadataOutputs)
                    .build();

        }

        throw new RuntimeException("Something went wrong");
    }


}
