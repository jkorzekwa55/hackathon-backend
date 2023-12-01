package com.room.hackathonbackend.service;

import com.befree.b3authauthorizationserver.B3authClient;
import com.befree.b3authauthorizationserver.B3authClientService;
import com.room.hackathonbackend.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@RequiredArgsConstructor
@Service
@Validated
public class ClientService implements B3authClientService {

    private final ClientRepository clientRepository;

    @Override
    public B3authClient findById(Long id) {
        return clientRepository.findById(id)
                .orElse(null);
    }

    @Override
    public B3authClient findByLogin(String login) {
        return clientRepository.findByLogin(login)
                .orElse(null);
    }
}
