package com.dir_music.metadata_service.service.metadata_service.input;

import com.dir_music.metadata_service.service.foundation.IServiceInput;
import lombok.*;


@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MetadataServiceSearchInput implements IServiceInput {
    private String searchQuery;
}
