package com.br.paulohbs.registraion_cd.service;

import com.br.paulohbs.registraion_cd.dto.CdDTO;
import com.br.paulohbs.registraion_cd.dto.CdTrackDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CdService {

    Page<CdDTO> getAllCds(final Pageable pageable);

    CdDTO creatingCollection(final CdDTO cdDTO);

    Page<CdDTO> searchCdsByFilters(final Pageable pageable, final String title, final String artist,
                                            final String genre, final String songName);

    void deleteCd(final Long id);

    void deleteTrackFromCd(final Long cdId, final Long trackId);

    CdDTO updateCd(final Long cdId,final CdDTO cdDTO);

    CdTrackDTO updateTrack(final Long cdId,final Long trackId,final CdTrackDTO trackDTO);
}
