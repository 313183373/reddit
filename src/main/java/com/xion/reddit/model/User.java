package com.xion.reddit.model;

import com.xion.reddit.model.validator.PasswordsMatch;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

@Entity
@Getter @Setter
@NoArgsConstructor
@RequiredArgsConstructor
@PasswordsMatch
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    @Column(nullable = false, unique = true)
    @Size(min = 8, max = 20)
    @Email
    private String email;

    @NonNull
    @Column(length = 100)
    private String password;

    @Transient
    @NotEmpty(message = "Please enter confirm password!")
    private String confirmPassword;

    @NonNull
    @Column(nullable = false)
    private boolean enabled;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Collection<Role> roles = new HashSet<>();

    @NonNull
    @NotEmpty(message = "Please enter First Name!")
    private String firstName;

    @NonNull
    @NotEmpty(message = "Please enter Last Name!")
    private String lastName;

    @Transient
    @Setter(AccessLevel.NONE)
    private String fullName;

    @NonNull
    @NotEmpty(message = "Please enter alias")
    @Column(nullable = false, unique = true)
    private String alias;

    private String activationCode;

    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    public void addRole(Role userRole) {
        roles.add(userRole);
//        userRole.getUsers().add(this);
    }

    public void addRoles(HashSet<Role> roles) {
        roles.forEach(this::addRole);
    }
}
