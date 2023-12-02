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

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Validated
@AllArgsConstructor
public class UserService implements B3authUserService {

    private UserRepository userRepository;
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

    public List<EventResponseDto> getUserNotifications(Authentication authentication) throws DirectException {
        User user = userRepository.findById((Long) authentication.getPrincipal())
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


}
