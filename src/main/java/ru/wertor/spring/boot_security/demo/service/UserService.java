package ru.wertor.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.wertor.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    User findById(Long id);

    List<User> findAll();

    User saveUser(User user);

    void deleteById(Long id);

    User findByLogin(String login);
    UserDetails loadUserByUsername(String login);

    Long loggedUserId();
}
