package com.room.hackathonbackend.service;

import com.room.hackathonbackend.dto.EventDto;
import com.room.hackathonbackend.dto.UserLocationDto;
import com.room.hackathonbackend.entity.Event;
import com.room.hackathonbackend.repository.EventRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class EventService {
    private EventRepository eventRepository;
    private ModelMapper modelMapper;

    public List<EventDto> getEventsInRadius(UserLocationDto userLocation, Integer radius, Authentication authentication) {

        List<EventDto> closeEvents = Arrays.asList();

        for (Event event : eventRepository.findAll()) {
            double dLat = Math.toRadians(event.getLatitude() - userLocation.getLatitude());
            double dLon = Math.toRadians(event.getLongitude() - userLocation.getLongitude());

            double lat1 = Math.toRadians(userLocation.getLatitude());
            double lat2 = Math.toRadians(event.getLatitude());

            double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2);
            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

            if (c * 6371 <= radius)
                closeEvents.add(modelMapper.map(event, EventDto.class));
        }

        return closeEvents;
    }
}
