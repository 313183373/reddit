package com.xion.reddit.model;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Getter @Setter
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
