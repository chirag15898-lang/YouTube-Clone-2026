package com.youtube.central.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserWatchHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    UUID userId;
    String videoId;
    int Count;
    LocalDateTime lastWatched;

}
