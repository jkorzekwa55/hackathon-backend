package com.room.hackathonbackend.entity;

import com.befree.b3authauthorizationserver.B3authRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Role implements B3authRole {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 45)
    private String name;

    private Boolean isDeleted;

    private LocalDateTime created;
    private String value;

    @ManyToOne
    @JoinColumn(name="owner_id")
    private User owner;

    @ManyToMany(mappedBy = "roles")
    private List<User> users = new ArrayList<>();

    @ManyToMany
    private List<Permission> permissions = new ArrayList<>();

    @Override
    public Long getOwnerId() {
        return owner.getId();
    }
    @Override
    public String getAuthority() {
        return "ROLE_" + value;
    }
}