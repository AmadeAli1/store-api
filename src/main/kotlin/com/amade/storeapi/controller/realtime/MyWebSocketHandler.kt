package com.amade.storeapi.controller.realtime

import com.amade.storeapi.model.User
import org.springframework.stereotype.Component
import org.springframework.web.reactive.socket.WebSocketHandler
import org.springframework.web.reactive.socket.WebSocketSession
import reactor.core.publisher.Mono
import reactor.core.publisher.Sinks.Many

@Component
class MyWebSocketHandler constructor(private val sinks: Many<User>) : WebSocketHandler {
    override fun handle(session: WebSocketSession): Mono<Void> {
        val data = sinks.asFlux()
            .map { user: User -> session.textMessage(user.toJson()) }
        return session.send(data)
    }
}