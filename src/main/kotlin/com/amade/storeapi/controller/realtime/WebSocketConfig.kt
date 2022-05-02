package com.amade.storeapi.controller.realtime

import com.amade.storeapi.model.User
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping
import org.springframework.web.reactive.socket.WebSocketHandler
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter
import reactor.core.publisher.Sinks
import reactor.core.publisher.Sinks.Many

@Configuration
class WebSocketConfig {
    @Bean
    fun handlerMapping(wsh: WebSocketHandler?): SimpleUrlHandlerMapping {
        val map = mutableMapOf<String, WebSocketHandler>()
        map["/ws/realtime"] = wsh!!
        return SimpleUrlHandlerMapping(map, 1)
    }

    @Bean
    fun webSocketHandlerAdapter(): WebSocketHandlerAdapter {
        return WebSocketHandlerAdapter()
    }

    @Bean
    fun sink(): Many<User> {
        return Sinks.many().multicast().directBestEffort()
    }
}