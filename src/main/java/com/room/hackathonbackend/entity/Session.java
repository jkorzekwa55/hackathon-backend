package com.room.hackathonbackend.entity;

import com.befree.b3authauthorizationserver.B3authSession;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

@Entity
@Table(name = "app_session")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Session implements B3authSession {
    @Id
    private UUID id;
    @ManyToOne
    @JoinColumn(name="app_user")
    private User userSubject;
    @ManyToOne
    @JoinColumn(name="client")
    private Client clientSubject;
    private String type;
    private LocalDateTime issuedAt;
    private LocalDateTime expiresAt;
    private LocalDateTime refreshExpiresAt;
    private Boolean deleted;
    private Boolean revoked;
    private Boolean suspended;

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public Long getSubjectId() {
        return userSubject.getId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userSubject.getRoles();
    }
}
