package org.example.deliveryapplication.ui.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class HomeUIController {
    
    @GetMapping("/")
    fun home(): String {
        return "home"
    }
} 