package ru.wertor.bootstrap.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.wertor.bootstrap.demo.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
