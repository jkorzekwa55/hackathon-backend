package com.room.hackathonbackend.controller;

import com.directai.directaiexceptionhandler.exception.DirectException;
import com.room.hackathonbackend.dto.EventResponseDto;
import com.room.hackathonbackend.dto.UserDto;
import com.room.hackathonbackend.dto.EventDto;
import com.room.hackathonbackend.dto.UserLocationDto;
import com.room.hackathonbackend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PutMapping("/{id}")
    public ResponseEntity<Optional<UserDto>> getUser(@PathVariable Long id){
        return ResponseEntity.ok(userService.getUser(id));
    }

    @GetMapping
    public String test() {
        return "test123";
    }

    @GetMapping("/{id}/notifications")
    public ResponseEntity<List<EventResponseDto>> getNotifications(@PathVariable("id") Long id, Authentication authentication) throws DirectException {
        return ResponseEntity.ok(userService.getUserNotifications(id, authentication));
    }

    @GetMapping("/{id}/events/{eventId}")
    public ResponseEntity<EventDto> getEvent(@PathVariable("id") Long id, @PathVariable("eventId") Long eventId, Authentication authentication) throws DirectException {
        return ResponseEntity.ok(userService.getUserEvent(id, eventId, authentication));
    }

    @GetMapping("/events/area/{radius}")
    public ResponseEntity<List<EventDto>> getEventsInRadius(@RequestBody UserLocationDto userLocation, @PathVariable("radius") Integer radius, Authentication authentication){
        return ResponseEntity.ok(userService.getEventsInRadius(userLocation, radius, authentication));
    }
}
