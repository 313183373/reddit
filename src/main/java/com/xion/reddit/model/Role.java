package com.xion.reddit.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Role {

    @Id @GeneratedValue
    private Long id;

    @NonNull
    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;
}
