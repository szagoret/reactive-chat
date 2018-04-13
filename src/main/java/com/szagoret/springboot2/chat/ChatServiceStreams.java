package com.szagoret.springboot2.chat;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface ChatServiceStreams {

    String NEW_COMMENTS = "newComments";
    String CLIENT_TO_BROKER = "clientToBroker";
    String BROKER_TO_CLIENT = "brokerToClient";

    String USER_HEADER = "User";


    /**
     * Use to consume messages
     *
     * @return
     */
    @Input(NEW_COMMENTS)
    SubscribableChannel newComments();

    /**
     * Use to transmit messages
     *
     * @return
     */
    @Output(CLIENT_TO_BROKER)
    MessageChannel clientToBroker();

    /**
     * Use to consume messages
     *
     * @return
     */
    @Input(BROKER_TO_CLIENT)
    SubscribableChannel brokerToClient();
}
