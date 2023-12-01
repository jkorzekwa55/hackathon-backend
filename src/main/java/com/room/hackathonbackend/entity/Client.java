package com.room.hackathonbackend.entity;

import com.befree.b3authauthorizationserver.B3authClient;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Client implements B3authClient {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(length = 45)
    private String name;
    @Column(length = 45)
    private String login;
    @Column(length = 300)
    private String password;
    private LocalDateTime created;

    private boolean disabled;
    private boolean initialised;
    private boolean deleted;
    private boolean locked;
    private boolean banned;
    private boolean suspended;

    @OneToMany(mappedBy = "clientSubject")
    private Set<Session> sessions;

    @Override
    public boolean getInitialised() {
        return initialised;
    }

    @Override
    public boolean getSuspended() {
        return suspended;
    }

    @Override
    public boolean getBanned() {
        return banned;
    }

    @Override
    public boolean getLocked() {
        return locked;
    }

    @Override
    public boolean getDeleted() {
        return deleted;
    }
}
