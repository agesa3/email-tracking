package com.emailtracking.email.tracking.repository;

import com.emailtracking.email.tracking.model.Email;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<Email, Long> {
}
