package com.amade.storeapi.controller.realtime;

import com.amade.storeapi.model.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@Component
public class MyWebSocketHandler implements WebSocketHandler {
    private final Sinks.Many<User> sinks;

    @Autowired
    public MyWebSocketHandler(Sinks.Many<User> sinks) {
        this.sinks = sinks;
    }

    @NotNull
    @Override
    public Mono<Void> handle(WebSocketSession session) {
        var data = sinks.asFlux()
                .map(user -> session.textMessage(user.toString()));
        return session.send(data);
    }

}
