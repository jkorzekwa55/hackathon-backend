package com.room.hackathonbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDataFillDto {
    private String name;
    private int birthDate;
    private String socialMediaLink;

}
