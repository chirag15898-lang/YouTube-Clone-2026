package com.youtube.central.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "videos")
public class Video
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    String id; // This id will be generated inside the Firebase
    String name;
    String description;
    LocalDateTime uploadDateTime;
    LocalDateTime updatedAt;
    String videoLink;
    String thumbnailLink;
    @OneToMany
    List<Tag> tags;

}
