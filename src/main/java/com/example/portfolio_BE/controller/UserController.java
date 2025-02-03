package com.example.portfolio_BE.controller;

import com.example.portfolio_BE.dto.UserDTO;
import com.example.portfolio_BE.model.User;
import com.example.portfolio_BE.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private UserService adminService;

    @PostMapping("/register")
    public ResponseEntity<Object> registerAdmin(@RequestBody UserDTO adminDTO) {
        String result = adminService.registerAdmin(adminDTO);

        if (result.equals("User already exists")) {
            return ResponseEntity.status(400).body(
                    new ResponseMessage(400, "User already exists", null));
        } else if (result.equals("User registered successfully")) {
            return ResponseEntity.status(201).body(
                    new ResponseMessage(201, "User registered successfully", adminDTO));
        } else {
            return ResponseEntity.status(500).body(
                    new ResponseMessage(500, "Something went wrong", null));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> loginAdmin() {
        return ResponseEntity.ok(new ResponseMessage(200, "Login Successful", null));
    }


    @GetMapping
    public ResponseEntity<Object> getAllUsers() {
        return ResponseEntity.ok(new ResponseMessage(200, "Users fetched successfully", adminService.getAllUsers()));
    }


    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable UUID id) {
        Optional<User> user = adminService.getUserById(id);

        if (user.isPresent()) {
            return ResponseEntity.status(200).body(
                    new ResponseMessage(200, "User fetched successfully", user.get()));
        } else {
            return ResponseEntity.status(404).body(
                    new ResponseMessage(404, "User not found", null));
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable UUID id, @RequestBody UserDTO adminDTO) {
        String result = adminService.updateUser(id, adminDTO);

        if (result.equals("User not found")) {
            return ResponseEntity.status(404).body(
                    new ResponseMessage(404, "User not found", null));
        } else if (result.equals("User updated successfully")) {
            return ResponseEntity.status(200).body(
                    new ResponseMessage(200, "User updated successfully", adminDTO));
        } else {
            return ResponseEntity.status(500).body(
                    new ResponseMessage(500, "Something went wrong", null));
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable UUID id) {
        String result = adminService.deleteUser(id);

        if (result.equals("User not found")) {
            return ResponseEntity.status(404).body(
                    new ResponseMessage(404, "User not found", null));
        } else if (result.equals("User deleted successfully")) {
            return ResponseEntity.status(200).body(
                    new ResponseMessage(200, "User deleted successfully", null));
        } else {
            return ResponseEntity.status(500).body(
                    new ResponseMessage(500, "Something went wrong", null));
        }
    }


    public static class ResponseMessage {
        private int statusCode;
        private String msg;
        private Object response;

        public ResponseMessage(int statusCode, String msg, Object response) {
            this.statusCode = statusCode;
            this.msg = msg;
            this.response = response;
        }

        public int getStatusCode() {
            return statusCode;
        }

        public String getMsg() {
            return msg;
        }

        public Object getResponse() {
            return response;
        }
    }
}
