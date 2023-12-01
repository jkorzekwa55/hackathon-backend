package com.room.hackathonbackend.entity;

import com.befree.b3authauthorizationserver.B3authAuthenticationAttempt;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class AuthenticationAttempt implements B3authAuthenticationAttempt {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "app_user")
    private User user;
    private String code;
    private LocalDateTime created;
    private boolean deleted;
    private boolean succeed;
    private boolean revoked;

    @Override
    public Long getUserId() {
        return user.getId();
    }
}
