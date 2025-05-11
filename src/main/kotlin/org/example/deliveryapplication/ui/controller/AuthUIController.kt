package org.example.deliveryapplication.ui.controller

import org.example.deliveryapplication.model.User
import org.example.deliveryapplication.service.UserService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@Controller
@RequestMapping("/auth")
class AuthUIController(private val userService: UserService) {

    @GetMapping("/login")
    fun loginForm(): String = "login"

    @PostMapping("/login")
    fun login(
        @RequestParam username: String,
        @RequestParam password: String,
        model: Model
    ): String {
        val user = userService.authenticate(username, password)
        return if (user != null) {
            "redirect:/"
        } else {
            model.addAttribute("error", "Invalid username or password")
            "login"
        }
    }

    @GetMapping("/register")
    fun registerForm(model: Model): String {
        model.addAttribute("user", User())
        return "register"
    }

    @PostMapping("/register")
    fun register(
        @Valid @ModelAttribute user: User,
        bindingResult: BindingResult,
        @RequestParam confirmPassword: String,
        model: Model
    ): String {
        if (bindingResult.hasErrors()) {
            return "register"
        }

        if (user.password != confirmPassword) {
            model.addAttribute("error", "Passwords do not match")
            return "register"
        }

        if (userService.getUserByUsername(user.username) != null) {
            model.addAttribute("error", "Username already exists")
            return "register"
        }

        if (userService.getUserByEmail(user.email) != null) {
            model.addAttribute("error", "Email already exists")
            return "register"
        }

        userService.createUser(user)
        return "redirect:/auth/login"
    }

    @GetMapping("/logout")
    fun logout(): String {
        // Implement logout logic here
        return "redirect:/auth/login"
    }
} 