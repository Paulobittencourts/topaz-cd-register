package com.br.paulohbs.registraion_cd.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cd_tracks")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TracksModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String trackName;

    private String artist;

    @ManyToOne
    @JoinColumn(name = "cd_id", referencedColumnName = "id", nullable = false)
    private CdModel cd;
}
