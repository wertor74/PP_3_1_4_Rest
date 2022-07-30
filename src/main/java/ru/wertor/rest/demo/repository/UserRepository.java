package ru.wertor.rest.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.wertor.rest.demo.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT u FROM User u JOIN FETCH u.role r WHERE u.login = :login")
    User findByLogin(String login);

    @Query(value = "SELECT u.password FROM User u WHERE u.id = :id")
    String getPasswordById(Long id);
}
