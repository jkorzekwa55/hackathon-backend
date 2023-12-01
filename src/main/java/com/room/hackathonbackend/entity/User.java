package com.room.hackathonbackend.entity;

import com.befree.b3authauthorizationserver.B3authUser;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "app_user")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class User implements B3authUser {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;
    @Column(length = 45)
    private String name;
    @Column(length = 45)
    private String email;
    private String socialMediaLink;
    private LocalDateTime created;

    private boolean disabled;
    private boolean initialised;
    private boolean deleted;
    private boolean locked;
    private boolean banned;
    private boolean suspended;

    @OneToMany(mappedBy = "userSubject")
    private Set<Session> sessions;

    @ManyToMany(fetch = FetchType.EAGER )
    private Set<Role> roles = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !suspended;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !banned;
    }

    @Override
    public boolean isEnabled() {
        return !disabled;
    }

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
