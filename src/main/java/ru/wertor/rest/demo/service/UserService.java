package ru.wertor.rest.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.wertor.rest.demo.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    User findById(Long id);

    List<User> findAll();

    User saveUser(User user);

    User updateUser(User user);

    void deleteUserById(Long id);

    UserDetails loadUserByUsername(String login);

    Long loggedUserId();
}