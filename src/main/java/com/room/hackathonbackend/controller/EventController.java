package com.room.hackathonbackend.controller;

import com.directai.directaiexceptionhandler.exception.DirectException;
import com.room.hackathonbackend.dto.EventDto;
import com.room.hackathonbackend.dto.EventPostDto;
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
    @PostMapping("/area/{radius}")
    public ResponseEntity<List<EventDto>> getEventsInRadius(@RequestBody UserLocationDto userLocation, @PathVariable("radius") Integer radius){
        return ResponseEntity.ok(eventService.getEventsInRadius(userLocation, radius));
    }

    @PostMapping
    public ResponseEntity<EventDto> addEvent(@RequestBody EventPostDto eventPostDto, Authentication authentication) throws DirectException {
        return ResponseEntity.ok(eventService.addEvent(eventPostDto, authentication));
    }
}
