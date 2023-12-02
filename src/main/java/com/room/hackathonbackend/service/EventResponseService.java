package com.room.hackathonbackend.service;

import com.directai.directaiexceptionhandler.DirectServerExceptionCode;
import com.directai.directaiexceptionhandler.exception.DirectException;
import com.room.hackathonbackend.dto.EventResponseDto;
import com.room.hackathonbackend.dto.EventResponsePostDto;
import com.room.hackathonbackend.entity.Event;
import com.room.hackathonbackend.entity.EventResponse;
import com.room.hackathonbackend.entity.User;
import com.room.hackathonbackend.repository.EventRepository;
import com.room.hackathonbackend.repository.EventResponseRepository;
import com.room.hackathonbackend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EventResponseService {
    private ModelMapper modelMapper;
    private EventResponseRepository eventResponseRepository;
    private UserRepository userRepository;
    private EventRepository eventRepository;

    public EventResponseDto addEventResponse(EventResponsePostDto responsePostDto, Authentication authentication) throws DirectException {
        if(authentication.getPrincipal() instanceof Long longId){
            User user = userRepository.findById(longId)
                    .orElseThrow(() -> new DirectException("User not found", "User with this id doesn't exists",
                            DirectServerExceptionCode.D4003, HttpStatus.NOT_FOUND));
            Event event = eventRepository.findById(responsePostDto.getEventId())
                    .orElseThrow(() -> new DirectException("Event not found", "Event with this id doesn't exists",
                            DirectServerExceptionCode.D4003, HttpStatus.NOT_FOUND));
            EventResponse eventResponse = EventResponse.builder()
                    .sender(user)
                    .message(responsePostDto.getMessage())
                    .event(event)
                    .build();

            return modelMapper.map(eventResponseRepository.save(eventResponse),EventResponseDto.class);
        }
        return null;
    }
}
