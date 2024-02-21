package com.example.mealplanner.controller;

import com.example.mealplanner.entity.User;
import com.example.mealplanner.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * This controller class handles user registration and login processes.
 */
@Controller
public class RegistrationController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    /**
     * Displays the registration form.
     * @return registration form view.
     */
    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register";
    }

    /**
     * Registers a new user and stores it in the database.
     * @param username the username of the new user.
     * @param password the password of the new user.
     * @param model the model object used for passing data to the view.
     * @return the view to display.
     */
    @PostMapping("/register")
    public String registerUser(@RequestParam("username") String username, @RequestParam("password") String password, Model model) {
        if (userRepository.findByUsername(username) != null) {
            model.addAttribute("errorMessage", "Username already exists.");
            return "register";
        }

        User newUser = new User(username, passwordEncoder.encode(password));
        userRepository.save(newUser);
        model.addAttribute("success", "Registration successful! You can now log in.");
        return "login";
    }

    /**
     * Displays the login form.
     * @return login form view.
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

}