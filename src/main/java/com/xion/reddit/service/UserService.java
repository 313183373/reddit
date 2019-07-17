package com.xion.reddit.service;

import com.xion.reddit.model.Role;
import com.xion.reddit.model.User;
import com.xion.reddit.repository.RoleRepository;
import com.xion.reddit.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final Logger logger = LoggerFactory.getLogger(UserService.class);
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private MailService mailService;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, MailService mailService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.mailService = mailService;
    }

    public User register(User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String secret = "{bcrypt}" + encoder.encode(user.getPassword());
        user.setPassword(secret);
        user.setConfirmPassword(secret);
        // add role
        Role role = roleRepository.findByName("ROLE_USER");
        user.addRole(role);
        // disable user
        user.setEnabled(false);
        // get validation code
        user.setActivationCode(UUID.randomUUID().toString());
        // save user
        save(user);
        // send validation email
        sendActivationEmail(user);
        return user;
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public void sendActivationEmail(User user) {
        mailService.sendActivationEmail(user);
    }

    public void sendWelcomeEmail(User user) {
        mailService.sendWelcomeEmail(user);
    }

    public Optional<User> findByEmailAndActivationCode(String email, String activationCode) {
        return userRepository.findByEmailAndActivationCode(email, activationCode);
    }
}
