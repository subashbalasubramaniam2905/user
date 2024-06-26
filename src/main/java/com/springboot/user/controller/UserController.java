package com.springboot.user.controller;

import com.springboot.user.model.User;
import com.springboot.user.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")

@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    //Dependency Injection
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/")
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/AP")
    public List<User> getUsersAP() {
         return userRepository.findByAddress("AP");
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @PostMapping("/")
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        return userRepository.save(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
    }

    @GetMapping("/external/{name}")
    public ResponseEntity<String> externalCall(@PathVariable  String name) {
        String externalAppUrl = "http://localhost:8083/awdhootTest/login/hello/"+name; // Replace with the actual URL
        ResponseEntity<String> response = restTemplate.getForEntity(externalAppUrl, String.class);
        return response;
    }


}
