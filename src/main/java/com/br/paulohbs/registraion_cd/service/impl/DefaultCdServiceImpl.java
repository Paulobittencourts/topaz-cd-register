package com.br.paulohbs.registraion_cd.service.impl;

import com.br.paulohbs.registraion_cd.dto.CdDTO;
import com.br.paulohbs.registraion_cd.exception.CdCollectionCreationException;
import com.br.paulohbs.registraion_cd.model.CdModel;
import com.br.paulohbs.registraion_cd.model.TracksModel;
import com.br.paulohbs.registraion_cd.repository.CdRepository;
import com.br.paulohbs.registraion_cd.service.CdService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultCdServiceImpl implements CdService {

    private final CdRepository cdRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public DefaultCdServiceImpl(CdRepository cdRepository, ModelMapper modelMapper) {
        this.cdRepository = cdRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Page<CdDTO> getAllCds(final Pageable pageable) {
        final Page<CdModel> pageOfCds = cdRepository.findAll(pageable);

        final List<CdDTO> productDTOs = pageOfCds.getContent().stream()
                .map(cds -> modelMapper.map(cds, CdDTO.class))
                .toList();

        return new PageImpl<>(productDTOs, pageable, pageOfCds.getTotalElements());
    }

    @Override
    public CdDTO creatingCollection(final CdDTO cdDTO) {
        try {
            final CdModel cdModel = modelMapper.map(cdDTO, CdModel.class);

            if (cdModel.getTracks() != null) {
                cdModel.getTracks().forEach(track -> track.setCd(cdModel));
            }

            final CdModel savedCdModel = cdRepository.save(cdModel);
            return modelMapper.map(savedCdModel, CdDTO.class);
        } catch (Exception e) {
            throw new CdCollectionCreationException("Failed to save product: " + e.getMessage(), e);
        }
    }

    @Override
    public Page<CdDTO> searchCdsByFilters(final Pageable pageable, final String title, final String artist,
                                          final String genre, final String songName) {

        final Page<CdModel> cdModels = cdRepository.findByFilters(title, artist, genre, songName, pageable);

        return cdModels.map(cd -> modelMapper.map(cd, CdDTO.class));
    }

    @Override
    public void deleteCd(Long id) {
        final CdModel cd = cdRepository.findById(id).orElseThrow(() -> new RuntimeException("CD not found"));
        cdRepository.delete(cd);
    }

    @Override
    public void deleteTrackFromCd(final Long cdId,final Long trackId) {
        final CdModel cd = cdRepository.findById(cdId)
                .orElseThrow(() -> new RuntimeException("CD not found"));

        final TracksModel trackToDelete = cd.getTracks().stream()
                .filter(track -> track.getId().equals(trackId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Track not found in this CD"));

        cd.getTracks().remove(trackToDelete);

        cdRepository.save(cd);
    }
}
