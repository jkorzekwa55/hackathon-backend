package com.room.hackathonbackend.service;

import com.befree.b3authauthorizationserver.B3authUser;
import com.befree.b3authauthorizationserver.B3authUserService;
import com.room.hackathonbackend.entity.User;
import com.room.hackathonbackend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@AllArgsConstructor
public class UserService implements B3authUserService {

    private UserRepository userRepository;

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
}
