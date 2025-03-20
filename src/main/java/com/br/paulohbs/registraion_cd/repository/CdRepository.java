package com.br.paulohbs.registraion_cd.repository;

import com.br.paulohbs.registraion_cd.model.CdModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CdRepository extends JpaRepository<CdModel, Long> {

    @Query("SELECT c FROM CdModel c JOIN c.tracks s WHERE " +
            "(:title IS NULL OR c.title LIKE %:title%) AND " +
            "(:artist IS NULL OR c.artist LIKE %:artist%) AND " +
            "(:genre IS NULL OR c.genre LIKE %:genre%) AND " +
            "(:songName IS NULL OR s.trackName LIKE %:songName%)")
    Page<CdModel> findByFilters(@Param("title") final String title,
                                       @Param("artist") final String artist,
                                       @Param("genre") final String genre,
                                       @Param("songName") final String songName,
                                       final Pageable pageable);
}
