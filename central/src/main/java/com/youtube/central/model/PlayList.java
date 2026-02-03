package com.youtube.central.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "playlist")

public class PlayList
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;
    @OneToOne
    Channel channel;
    @OneToMany
    List<Video> videos;



}
