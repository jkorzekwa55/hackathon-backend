package com.room.hackathonbackend.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.GenerationType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Blob;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 64)
    private String name;

    @ManyToOne
    private User creator;
    private LocalDateTime plannedOn;

    @Lob
    private Blob image;

    private double longitude;
    private double latitude;

    private boolean inProgress;
    private boolean happened;
    public boolean getInProgress(){ return inProgress; }
    public boolean getHappened(){ return happened; }
}