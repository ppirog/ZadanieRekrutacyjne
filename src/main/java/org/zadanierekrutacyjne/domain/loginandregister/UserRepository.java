package org.zadanierekrutacyjne.domain.loginandregister;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}