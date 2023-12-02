package com.room.hackathonbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String name;
    private int birthYear;
    private Boolean isDisabled;
    private Boolean isInitialized;
    private String socialMediaLink;
    private String email;
}