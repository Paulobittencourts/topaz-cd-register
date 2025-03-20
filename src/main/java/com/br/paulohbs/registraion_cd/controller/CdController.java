package com.br.paulohbs.registraion_cd.controller;


import com.br.paulohbs.registraion_cd.dto.CdDTO;
import com.br.paulohbs.registraion_cd.service.CdService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.br.paulohbs.registraion_cd.utils.PagesUtils.createPageable;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cds")
public class CdController {

    private CdService cdService;

    @Autowired
    public CdController(CdService cdService) {
        this.cdService = cdService;
    }

    @GetMapping
    public PagedModel<CdDTO> getAllCds(@RequestParam(required = false, defaultValue = "0") int page,
                                       @RequestParam(required = false, defaultValue = "10") int size) {

        final Page<CdDTO> pageOfCds = cdService.getAllCds(createPageable(page, size));
        return new PagedModel<>(pageOfCds);
    }

    @PostMapping("/save-cds")
    public ResponseEntity<CdDTO> saveCds(@RequestBody @Validated CdDTO cdDTO) {
        try {
            final CdDTO savedCd = cdService.creatingCollection(cdDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCd);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<PagedModel<CdDTO>> searchCds(@RequestParam(required = false, defaultValue = "0") int page,
                                                       @RequestParam(required = false, defaultValue = "10") int size,
                                                       @RequestParam(required = false) String title,
                                                       @RequestParam(required = false) String artist,
                                                       @RequestParam(required = false) String genre,
                                                       @RequestParam(required = false) String songName) {

        final Page<CdDTO> pageOfCdsFilters = cdService.searchCdsByFilters(createPageable(page, size), title, artist, genre, songName);
        if (pageOfCdsFilters.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(new PagedModel<>(pageOfCdsFilters));
    }

    @DeleteMapping("/{cdId}")
    public ResponseEntity<String> deleteCd(@PathVariable Long cdId) {
        try {
            cdService.deleteCd(cdId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("CD and associated tracks deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while deleting CD: " + e.getMessage());
        }
    }

    @DeleteMapping("/{cdId}/tracks/{trackId}")
    public ResponseEntity<String> deleteTrackFromCd(@PathVariable Long cdId, @PathVariable Long trackId) {
        try {
            cdService.deleteTrackFromCd(cdId, trackId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Track deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while deleting track: " + e.getMessage());
        }
    }
}
