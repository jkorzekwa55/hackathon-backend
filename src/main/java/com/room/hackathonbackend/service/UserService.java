package com.room.hackathonbackend.service;

import com.befree.b3authauthorizationserver.B3authUser;
import com.befree.b3authauthorizationserver.B3authUserService;
import com.directai.directaiexceptionhandler.DirectServerExceptionCode;
import com.directai.directaiexceptionhandler.exception.DirectException;
import com.room.hackathonbackend.dto.*;
import com.room.hackathonbackend.entity.Event;
import com.room.hackathonbackend.entity.User;
import com.room.hackathonbackend.repository.EventRepository;
import com.room.hackathonbackend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Validated
@AllArgsConstructor
public class UserService implements B3authUserService {

    private UserRepository userRepository;
    private EventRepository eventRepository;
    private ModelMapper modelMapper;

    public Optional<UserDto> getUser(Long id) {
       return userRepository.findById(id).map(user -> modelMapper.map(user, UserDto.class));
    }

    public UserDto fillDataUser(UserDataFillDto userDto, Authentication authentication) throws DirectException {
        if(authentication.getPrincipal() instanceof Long longId){
            User user = userRepository.findById(longId)
                    .orElseThrow(() -> new DirectException("User not found", "User with this id doesn't exists",
                            DirectServerExceptionCode.D4003, HttpStatus.NOT_FOUND));
            User updatedUser = User.builder()
                    .id(user.getId())
                    .name(userDto.getName())
                    .birthYear(userDto.getBirthYear())
                    .socialMediaLink(userDto.getSocialMediaLink())
                    .initialised(true)
                    .build();
            userRepository.save(updatedUser);
            return modelMapper.map(updatedUser, UserDto.class);
        }
        return null;
    }

    @Override
    public void createAndSaveByEmail(String email) {
        User user = User.builder()
                .email(email)
                .initialised(false)
                .build();

        userRepository.save(user);
    }


    @Override
    public B3authUser findById(Long id) {
        return userRepository.findById(id)
                .orElse(null);
    }

    @Override
    public B3authUser findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElse(null);
    }

    public List<EventResponseDto> getUserNotifications(Long id, Authentication authentication) throws DirectException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new DirectException("User not found", "", DirectServerExceptionCode.D4000));
        return user.getReceivedNotifications().stream().map(r -> modelMapper.map(r, EventResponseDto.class)).toList();

    }

    public EventDto getUserEvent(Long id, Long eventId, Authentication authentication) throws DirectException{
        User user = userRepository.findById(id)
                .orElseThrow(() -> new DirectException("User not found","", DirectServerExceptionCode.D4000));

        for (Event event : user.getEvents()) {
            if (Objects.equals(event.getId(), eventId))
                return modelMapper.map(event, EventDto.class);
        }
        return null;
    }

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
