package com.dir_music.metadata_service.repository.metadata_repository;


import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "metadata")
public class MetadataModel {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Nonnull
    private Long id;

    @Column
    private String title;

    @Column
    private String artist;

    @Column
    private String album;

    @Column
    private String genre;

    @Column
    private String year;

    @Column
    private Long durationMillis;

    @Column
    private Long sizeBytes;
}
