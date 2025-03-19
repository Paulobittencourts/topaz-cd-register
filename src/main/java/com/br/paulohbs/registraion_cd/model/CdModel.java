package com.br.paulohbs.registraion_cd.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.util.List;

@Entity
@Table(name = "cd_collection")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CdModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String artist;

    private Time duration;

    @OneToMany(mappedBy = "cd", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TracksModel> tracks;

}
