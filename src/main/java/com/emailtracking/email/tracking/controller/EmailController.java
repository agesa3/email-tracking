package com.emailtracking.email.tracking.controller;


import com.emailtracking.email.tracking.model.Email;
import com.emailtracking.email.tracking.repository.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.http.ResponseEntity.noContent;

@RestController
@RequestMapping("/api/emails")
public class EmailController {
    @Autowired
    private EmailRepository emailRepository;

    @GetMapping
    public List<Email> getAllEmails() {
        return emailRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Email> getEmailById(@PathVariable Long id) {
        return emailRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Email createEmail(@RequestBody Email email) {
        email.setReceivedAt(LocalDateTime.now());
        return emailRepository.save(email);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Email> updateEmail(@PathVariable Long id, @RequestBody Email updatedEmail) {
        return emailRepository.findById(id)
                .map(email -> {
                    email.setSubject(updatedEmail.getSubject());
                    email.setBody(updatedEmail.getBody());
                    Email saved = emailRepository.save(email);
                    return ResponseEntity.ok(saved);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmail(@PathVariable Long id) {
        return emailRepository.findById(id)
                .map(email -> {
                    emailRepository.delete(email);
                    return ResponseEntity<Void>.noContent()
                            .build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/read")
    public ResponseEntity<Email> markEmailAsRead(@PathVariable Long id) {
        return emailRepository.findById(id)
                .map(email -> {
                    email.setReadAt(LocalDateTime.now());
                    Email saved = emailRepository.save(email);
                    return ResponseEntity.ok(saved);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
