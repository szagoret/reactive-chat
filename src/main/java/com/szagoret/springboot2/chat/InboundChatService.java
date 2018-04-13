package com.szagoret.springboot2.chat;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

/**
 * EnableBinding - signals Spring Cloud Stream to connect this component to its broker-handling machinery
 */
@Service
@EnableBinding(ChatServiceStreams.class)
public class InboundChatService extends UserParsingHandshakeHandler {

    private final ChatServiceStreams chatServiceStreams;

    public InboundChatService(ChatServiceStreams chatServiceStreams) {
        this.chatServiceStreams = chatServiceStreams;
    }

    /**
     * To handle a new WebSocketSession, we grab it and invoke its receive() method.
     * This hands us a Flux of potentially endless WebSocketMessage objects.
     * These would be the incoming messages sent in by the client that just connected.
     * NOTE: Every client that connects will invoke this method independently.
     * <p>
     * To hand control to the framework, we put a then() on the tail of this Reactor flow so Spring can subscribe to this Flux.
     *
     * @param session
     * @return
     */
    @Override
    protected Mono<Void> handleInternal(WebSocketSession session) {
        return session
                .receive()
                .log(getUser(session.getId()) + "-inbound-incoming-chat-message")
                .map(WebSocketMessage::getPayloadAsText)
                .log(getUser(session.getId()) + "-inbound-convert-to-text")
//                .map(s -> session.getId() + ":" + s)
//                .log("inbound-mark-with-session-id")
                .flatMap(message -> broadcast(message, getUser(session.getId())))
                .log(getUser(session.getId()) + "-inbound-broadcast-to-broker")
                .then();
    }

    public Mono<?> broadcast(String message, String user) {
        return Mono.fromRunnable(() -> {
            chatServiceStreams.clientToBroker()
                    .send(MessageBuilder.withPayload(message)
                            .setHeader(ChatServiceStreams.USER_HEADER, user)
                            .build());
        });
    }


}
