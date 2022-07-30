package ru.wertor.rest.demo.service;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.wertor.rest.demo.repository.UserRepository;
import ru.wertor.rest.demo.model.User;
import java.util.List;

@Service
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImp(UserRepository userRepository, @Lazy BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Transactional
    @Override
    public User findById(Long id) {
        return userRepository.findById(id).get();
    }

    @Transactional
    @Override
    public List<User> findAll () {
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public User saveUser(User user) {
        user.setRole(user.getRole());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Transactional
    @Override
    public User updateUser(User user) {
        user.setRole(user.getRole());
        if (!user.getPassword().equals(userRepository.getPasswordById(user.getId()))) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }
        return userRepository.save(user);
    }

    @Transactional
    @Override
    public void deleteUserById (Long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(login);
        if (user == null) {
            throw new UsernameNotFoundException("Пользователь не найден");
        }
        return user;
    }

    // получаем Id зарегистрированного пользователя
    @Transactional
    @Override
    public Long loggedUserId() {
        User loggedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return loggedUser.getId();
    }
}