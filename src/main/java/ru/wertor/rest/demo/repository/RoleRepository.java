package ru.wertor.rest.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.wertor.rest.demo.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
