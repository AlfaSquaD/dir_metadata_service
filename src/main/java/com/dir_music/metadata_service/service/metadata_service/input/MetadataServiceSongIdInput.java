package com.dir_music.metadata_service.service.metadata_service.input;

import com.dir_music.metadata_service.service.foundation.IServiceInput;
import lombok.*;

import java.util.List;


@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MetadataServiceSongIdInput implements IServiceInput {
    private List<Long> songIds;
}
