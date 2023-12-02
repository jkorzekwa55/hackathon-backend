package com.room.hackathonbackend.entity;

import jakarta.persistence.GenerationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Blob;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
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
    private double magnitude;

    private boolean inProgress;
    private boolean happened;
    public boolean getInProgress(){ return inProgress; }
    public boolean getHappened(){ return happened; }
}