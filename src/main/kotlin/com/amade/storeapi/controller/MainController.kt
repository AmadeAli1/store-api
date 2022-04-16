package com.amade.storeapi.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.kotlin.core.publisher.toMono

@RestController
@RequestMapping("/main")
class MainController {

    @GetMapping
    fun sayHello() = "Hello Amade Ali".toMono()


}