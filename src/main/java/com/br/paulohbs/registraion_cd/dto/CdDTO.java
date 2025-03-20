package com.br.paulohbs.registraion_cd.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CdDTO {
    private Long id;
    private String title;
    private String artist;
    private String genre;
    private List<CdTrackDTO> tracks;
}
