package com.dir_music.metadata_service.service.metadata_service;

import com.dir_music.metadata_service.service.foundation.IService;
import com.dir_music.metadata_service.service.metadata_service.input.MetadataServiceCreateMusicInput;
import com.dir_music.metadata_service.service.metadata_service.input.MetadataServiceSearchInput;
import com.dir_music.metadata_service.service.metadata_service.input.MetadataServiceSongIdInput;
import com.dir_music.metadata_service.service.metadata_service.output.MetadataServiceMetadataListOutput;
import org.springframework.stereotype.Service;


@Service
public interface MetadataService extends IService {
    MetadataServiceMetadataListOutput searchMusic(MetadataServiceSearchInput searchInput);

    String createMusic(MetadataServiceCreateMusicInput createMusicInput);

    MetadataServiceMetadataListOutput getListedMusic(MetadataServiceSongIdInput songIdInput);
}
