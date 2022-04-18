package com.amade.storeapi.html

import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/")
@RestController
class HomeController {

    @GetMapping
    suspend fun home(model: Model): String {
        return "home"
    }

}