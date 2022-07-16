package ru.wertor.spring.boot_security.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.wertor.spring.boot_security.demo.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
