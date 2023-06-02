package com.dir_music.metadata_service.repository.metadata_repository;

import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MetadataRepository extends JpaRepository<MetadataModel, Long>{

    Optional<List<MetadataModel>> findByAlbumIsLikeIgnoreCase(String album);

    Optional<List<MetadataModel>> findByArtistIsLikeIgnoreCase(String artist);

    Optional<List<MetadataModel>> findByGenreIsLikeIgnoreCase(String genre);

    Optional<List<MetadataModel>> findByTitleIsLikeIgnoreCase(String title);


    @Query(value = "SELECT * FROM metadata WHERE" +
            " title LIKE %:query% OR artist LIKE %:query% OR album LIKE %:query% OR genre LIKE %:query%",
            nativeQuery = true)
    Optional<List<MetadataModel>> searchMusic(@Nonnull String query);

    @Nonnull
    @Override
    Optional<MetadataModel> findById(@Nonnull Long id);

    @Override
    void deleteById(@Nonnull Long id);
}
