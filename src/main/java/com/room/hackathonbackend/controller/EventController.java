package com.room.hackathonbackend.controller;

import com.room.hackathonbackend.dto.EventDto;
import com.room.hackathonbackend.dto.UserLocationDto;
import com.room.hackathonbackend.service.EventService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/event")
@AllArgsConstructor
public class EventController {
    private EventService eventService;
    @GetMapping("/events/area/{radius}")
    public ResponseEntity<List<EventDto>> getEventsInRadius(@RequestBody UserLocationDto userLocation, @PathVariable("radius") Integer radius, Authentication authentication){
        return ResponseEntity.ok(eventService.getEventsInRadius(userLocation, radius, authentication));
    }
}
