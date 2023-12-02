package com.room.hackathonbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Blob;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventDto {

    private Long id;
    private String name;
    private UserDto creator;
    private LocalDateTime plannedOn;

    private Blob image;

    private double longitude;
    private double latitude;

    private boolean inProgress;
    private boolean happened;
}