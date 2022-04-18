package com.amade.storeapi.html

import com.amade.storeapi.model.Constants.HOMEPAGE
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.result.view.Rendering

@Controller
class HomeController {

    @GetMapping("/")
    suspend fun home(model: Model): Rendering {
        return Rendering.view(HOMEPAGE).build()
    }

}