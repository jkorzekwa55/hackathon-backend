package com.room.hackathonbackend.controller;

import com.directai.directaiexceptionhandler.exception.DirectException;
import com.room.hackathonbackend.dto.EventResponseDto;
import com.room.hackathonbackend.dto.EventResponsePostDto;
import com.room.hackathonbackend.service.EventResponseService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notification")
@AllArgsConstructor
public class EventResponseController {
    private EventResponseService eventResponseService;
    @PostMapping("/send")
    public ResponseEntity<EventResponseDto> sendResponseToEvent(@RequestBody EventResponsePostDto eventResponsePostDto, Authentication authentication) throws DirectException {
        return ResponseEntity.ok(eventResponseService.addEventResponse(eventResponsePostDto,authentication));
    }

}
