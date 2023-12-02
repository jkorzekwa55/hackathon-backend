package com.room.hackathonbackend.controller;

import com.directai.directaiexceptionhandler.exception.DirectException;
import com.room.hackathonbackend.dto.*;
import com.room.hackathonbackend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<Optional<UserDto>> getUser(@PathVariable Long id){
        return ResponseEntity.ok(userService.getUser(id));
    }

    @PostMapping("/data-fill")
    public ResponseEntity fillUserData(@RequestBody UserDataFillDto userDataFillDto, Authentication authentication) throws DirectException {
        return ResponseEntity.ok(userService.fillDataUser(userDataFillDto, authentication));
    }

    @GetMapping("/notifications")
    public ResponseEntity<List<EventResponseDto>> getNotifications(Authentication authentication) throws DirectException {
        return ResponseEntity.ok(userService.getUserNotifications(authentication));
    }

    @GetMapping("/{id}/events/{eventId}")
    public ResponseEntity<EventDto> getEvent(@PathVariable("id") Long id, @PathVariable("eventId") Long eventId, Authentication authentication) throws DirectException {
        return ResponseEntity.ok(userService.getUserEvent(id, eventId, authentication));
    }

}
