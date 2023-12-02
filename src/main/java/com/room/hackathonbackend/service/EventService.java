package com.room.hackathonbackend.service;

import com.directai.directaiexceptionhandler.DirectServerExceptionCode;
import com.directai.directaiexceptionhandler.exception.DirectException;
import com.room.hackathonbackend.dto.EventDto;
import com.room.hackathonbackend.dto.EventPostDto;
import com.room.hackathonbackend.dto.UserLocationDto;
import com.room.hackathonbackend.entity.Event;
import com.room.hackathonbackend.entity.User;
import com.room.hackathonbackend.repository.EventRepository;
import com.room.hackathonbackend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class EventService {
    private EventRepository eventRepository;
    private UserRepository userRepository;
    private ModelMapper modelMapper;

    public EventDto addEvent(EventPostDto eventPostDto, Authentication authentication) throws DirectException {
        if (authentication.getPrincipal() instanceof Long longId) {
            User user = userRepository.findById(longId)
                    .orElseThrow(() -> new DirectException("User not found", "User with this id doesn't exists",
                            DirectServerExceptionCode.D4003, HttpStatus.NOT_FOUND));
            Event event = Event.builder()
                    .creator(user)
                    .latitude(eventPostDto.getLatitude())
                    .longitude(eventPostDto.getLongitude())
                    .name(eventPostDto.getName())
                    .image(eventPostDto.getImage())
                    .plannedOn(eventPostDto.getPlannedOn())
                    .build();
            eventRepository.save(event);
            return modelMapper.map(event, EventDto.class);
        }
        return null;
    }

    public List<EventDto> getEventsInRadius(UserLocationDto userLocation, Integer radius) {

        List<EventDto> closeEvents = Arrays.asList();

        for (Event event : eventRepository.findAll()) {
            double dLat = Math.toRadians(event.getLatitude() - userLocation.getLatitude());
            double dLon = Math.toRadians(event.getLongitude() - userLocation.getLongitude());

            double lat1 = Math.toRadians(userLocation.getLatitude());
            double lat2 = Math.toRadians(event.getLatitude());

            double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2);
            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

            if (c * 6371 <= radius)
                System.out.println(event.getName());
                closeEvents.add(modelMapper.map(event, EventDto.class));
        }

        return closeEvents;
    }
}
