package com.room.hackathonbackend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserPutDto {
    @NotNull
    @Size(min = 2, max = 45, message = "user name should have at least 2 characters")
    private String name;
    @NotNull
    @Size(min = 2, max = 45, message = "user surname should have at least 2 characters")
    private String surname;
    @Size(min = 9, max = 10, message = "user phone number should have at least 9 characters")
    @NotNull
    private String phoneNumber;
    @NotNull
    private Boolean isDisabled;
    @NotNull
    private Boolean isInitialized;
    @Email
    @NotNull
    private String email;
}
