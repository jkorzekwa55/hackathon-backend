package com.room.hackathonbackend.service;

import com.befree.b3authauthorizationserver.B3authAuthenticationAttempt;
import com.befree.b3authauthorizationserver.B3authAuthenticationAttemptService;
import com.befree.b3authauthorizationserver.B3authUser;
import com.room.hackathonbackend.entity.AuthenticationAttempt;
import com.room.hackathonbackend.entity.User;
import com.room.hackathonbackend.repository.AuthenticationAttemptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthenticationAttemptService implements B3authAuthenticationAttemptService {
    private final AuthenticationAttemptRepository authenticationAttemptRepository;
    @Override
    public void save(B3authAuthenticationAttempt b3authAuthenticationAttempt) {
        if(b3authAuthenticationAttempt instanceof AuthenticationAttempt authenticationAttempt) {
            authenticationAttemptRepository.save(authenticationAttempt);
        }
    }

    @Override
    public void create(B3authUser b3authUser, String code) {
        if(b3authUser instanceof User user) {
            AuthenticationAttempt authenticationAttempt = new AuthenticationAttempt(null, user, code, LocalDateTime.now(), false, false, false);
            authenticationAttemptRepository.save(authenticationAttempt);
        }
    }

    public void remove(B3authAuthenticationAttempt b3authAuthenticationAttempt) {
        if(b3authAuthenticationAttempt instanceof AuthenticationAttempt authenticationAttempt) {
            authenticationAttemptRepository.delete(authenticationAttempt);
        }
    }

    public void removeById(Long id) {
        authenticationAttemptRepository.deleteById(id);
    }

    @Override
    public B3authAuthenticationAttempt findById(Long id) {
        return authenticationAttemptRepository.findById(id)
                .orElse(null);
    }

    @Override
    public B3authAuthenticationAttempt findLastAttemptByUserId(Long id) {
        return authenticationAttemptRepository.findLastByUserId(id)
                .orElse(null);
    }
}
