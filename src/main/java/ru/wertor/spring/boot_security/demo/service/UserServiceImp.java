package ru.wertor.spring.boot_security.demo.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.wertor.spring.boot_security.demo.model.User;
import ru.wertor.spring.boot_security.demo.repository.RoleRepository;
import ru.wertor.spring.boot_security.demo.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Service
public class UserServiceImp implements UserService {

    @PersistenceContext
    private EntityManager entityManager;

    private final UserRepository userRepository;

    public UserServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public User findById(Long id) {
        return userRepository.getOne(id);
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
        return userRepository.save(user);
    }

    @Transactional
    @Override
    public void deleteById (Long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    @Override
    public User findByLogin(String login) {
        TypedQuery<User> query = entityManager.createQuery("select u from User u WHERE u.login = :login", User.class)
                .setParameter("login", login);
        return query.getSingleResult();
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