package com.example.demo.controller;

import com.example.demo.dto.Message;
import com.example.demo.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;

    @GetMapping
    public List<Message> getAllMessages() {
        return (List<Message>) messageRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> getMessageById(@PathVariable int id) {
        return messageRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Message createMessage(@RequestBody Message message) {
        return messageRepository.save(message);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Message> updateMessage(@PathVariable int id, @RequestBody Message messageDetails) {
        return messageRepository.findById(id)
                .map(message -> {
                    message.setTitle(messageDetails.getTitle());
                    message.setText(messageDetails.getText());
                    message.setTime(messageDetails.getTime());
                    return ResponseEntity.ok(messageRepository.save(message));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable int id) {
        return messageRepository.findById(id)
                .map(message -> {
                    messageRepository.delete(message);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}