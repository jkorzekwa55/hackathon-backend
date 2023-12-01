package com.room.hackathonbackend.config;

import com.befree.b3authauthorizationserver.*;
import com.befree.b3authauthorizationserver.jwt.Jwt;
import com.room.hackathonbackend.entity.Client;
import com.room.hackathonbackend.entity.Session;
import com.room.hackathonbackend.entity.User;
import com.room.hackathonbackend.repository.ClientRepository;
import com.room.hackathonbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SessionGenerator implements B3authSessionGenerator {
    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    @Override
    public B3authSession generate(Jwt authorizationToken, Jwt refreshToken, B3authClientAuthorizationToken b3authClientAuthorizationToken) {
        Client client = clientRepository.findById(b3authClientAuthorizationToken.getClientId())
                .orElseThrow();

        return new Session(authorizationToken.getId(), null, client, B3authSessionType.CLIENT_SESSION,
                authorizationToken.getIssuedAt(), authorizationToken.getExpiresAt(),
                refreshToken.getExpiresAt(), false, false, false);
    }

    @Override
    public B3authSession generate(Jwt authorizationToken, Jwt refreshToken, B3authAuthorizationToken b3authAuthorizationToken) {
        User user = userRepository.findById(b3authAuthorizationToken.getUserId())
                .orElseThrow();

        return new Session(authorizationToken.getId(), user, null, B3authSessionType.USER_SESSION,
                authorizationToken.getIssuedAt(), authorizationToken.getExpiresAt(),
                refreshToken.getExpiresAt(), false, false, false);
    }
}
