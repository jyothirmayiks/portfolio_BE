package com.example.portfolio_BE.service;

import com.example.portfolio_BE.model.ContactMessage;
import com.example.portfolio_BE.repository.ContactMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactMessageService {

    @Autowired
    private ContactMessageRepository contactMessageRepository;


    public ContactMessage saveMessage(ContactMessage contactMessage) {
        return contactMessageRepository.save(contactMessage);
    }
    public List<ContactMessage> getAllMessages() {
        return contactMessageRepository.findAll();
    }
}
