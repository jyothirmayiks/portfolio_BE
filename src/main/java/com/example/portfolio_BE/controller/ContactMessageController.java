package com.example.portfolio_BE.controller;

import com.example.portfolio_BE.model.ContactMessage;
import com.example.portfolio_BE.service.ContactMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contact")
public class ContactMessageController {

    @Autowired
    private ContactMessageService contactMessageService;


    @PostMapping
    public ResponseEntity<String> saveContactMessage(@RequestBody ContactMessage contactMessage) {
        try {
            contactMessageService.saveMessage(contactMessage);
            return ResponseEntity.ok("Message sent successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to send message.");
        }
    }


    @GetMapping
    public ResponseEntity<List<ContactMessage>> getAllMessages() {
        try {
            List<ContactMessage> messages = contactMessageService.getAllMessages();
            return ResponseEntity.ok(messages);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }
}
