package com.room.hackathonbackend.entity;

import jakarta.persistence.GenerationType;
import jakarta.persistence.*;
import lombok.*;
import java.sql.Blob;
import java.time.LocalDateTime;
import java.util.Set;

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

    @OneToMany(mappedBy = "event")
    private Set<EventResponse> eventResponses;

    @Lob
    private Blob image;

    private double longitude;
    private double latitude;

    private boolean inProgress;
    private boolean happened;
    public boolean getInProgress(){ return inProgress; }
    public boolean getHappened(){ return happened; }
}