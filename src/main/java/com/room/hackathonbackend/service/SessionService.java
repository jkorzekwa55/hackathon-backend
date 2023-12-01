package com.room.hackathonbackend.service;

import com.befree.b3authauthorizationserver.B3authSession;
import com.befree.b3authauthorizationserver.B3authSessionService;
import com.room.hackathonbackend.entity.Session;
import com.room.hackathonbackend.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SessionService implements B3authSessionService {
    private final SessionRepository sessionRepository;
    @Override
    public void save(B3authSession b3authSession) {
        if(b3authSession instanceof Session session) {
            sessionRepository.save(session);
        }
    }

    @Override
    public void remove(B3authSession b3authSession) {
        if(b3authSession instanceof Session session) {
            sessionRepository.delete(session);
        }
    }

    @Override
    public void removeById(UUID uuid) {
        sessionRepository.deleteById(uuid);
    }

    @Override
    public B3authSession findById(UUID uuid) {
        return sessionRepository.findById(uuid)
                .orElse(null);
    }
}
