package com.example.portfolio_BE.service;

import com.example.portfolio_BE.dto.UserDTO;
import com.example.portfolio_BE.model.User;
import com.example.portfolio_BE.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository adminRepo;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Register Admin (Already provided)
    public String registerAdmin(UserDTO adminDTO) {
        if (adminRepo.findByUsername(adminDTO.getUsername()) != null) {
            return "User already exists";
        }

        String hashedPassword = passwordEncoder.encode(adminDTO.getPassword());

        User admin = new User();
        admin.setUsername(adminDTO.getUsername());
        admin.setPassword(hashedPassword);
        adminRepo.save(admin);

        return "User registered successfully";
    }

    // Login Admin (Assuming login is successful if user exists)
    public boolean loginAdmin(String username, String password) {
        User user = adminRepo.findByUsername(username);
        return user != null && passwordEncoder.matches(password, user.getPassword());
    }

    // Get All Users
    public List<User> getAllUsers() {
        return adminRepo.findAll();
    }

    // Get User by ID
    public Optional<User> getUserById(UUID id) {
        return adminRepo.findById(id);
    }

    // Update User Details
    public String updateUser(UUID id, UserDTO adminDTO) {
        Optional<User> existingUser = adminRepo.findById(id);

        if (existingUser.isPresent()) {
            User user = existingUser.get();
            user.setUsername(adminDTO.getUsername());
            if (adminDTO.getPassword() != null) {
                user.setPassword(passwordEncoder.encode(adminDTO.getPassword()));
            }
            adminRepo.save(user);
            return "User updated successfully";
        } else {
            return "User not found";
        }
    }

    // Delete User
    public String deleteUser(UUID id) {
        Optional<User> existingUser = adminRepo.findById(id);

        if (existingUser.isPresent()) {
            adminRepo.delete(existingUser.get());
            return "User deleted successfully";
        } else {
            return "User not found";
        }
    }

    public int getUserCount() {
        return (int) adminRepo.count();
    }
}
