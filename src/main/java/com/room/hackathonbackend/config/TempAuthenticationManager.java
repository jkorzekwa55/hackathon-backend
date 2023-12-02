package com.room.hackathonbackend.config;

import com.befree.b3authauthorizationserver.B3authSessionService;
import com.befree.b3authauthorizationserver.B3authUserService;
import com.befree.b3authauthorizationserver.authentication.B3authUserTokenAuthenticationProvider;
import com.befree.b3authauthorizationserver.jwt.JwtGenerator;
import com.room.hackathonbackend.service.SessionService;
import com.room.hackathonbackend.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class TempAuthenticationManager implements AuthenticationManager {
    private JwtGenerator jwtGenerator;
    private B3authUserService userService;
    private B3authSessionService sessionService;

    public TempAuthenticationManager(JwtGenerator jwtGenerator, B3authUserService userService, B3authSessionService sessionService) {
        this.jwtGenerator = jwtGenerator;
        this.userService = userService;
        this.sessionService = sessionService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println("works");
        return new B3authUserTokenAuthenticationProvider(jwtGenerator, sessionService, userService).authenticate(authentication);
    }
}
