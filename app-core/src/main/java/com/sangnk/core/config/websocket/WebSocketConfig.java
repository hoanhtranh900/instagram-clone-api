package com.sangnk.core.config.websocket;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Value("${allowedOrigins}")
    private String allowedOrigins;

    @Value("${allowedOrigins_ws}")
    private String allowedOrigins_ws;

    @Value("${allowedOrigins_ws_admin}")
    private String allowedOrigins_ws_admin;

    @Value("${allowedOrigins_ws_admin_domain}")
    private String allowedOrigins_ws_admin_domain;


    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOrigins(allowedOrigins, allowedOrigins_ws, allowedOrigins_ws_admin, allowedOrigins_ws_admin_domain)
                .withSockJS();
    }

    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
        registration.setSendTimeLimit(15 * 60 * 1000);
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app");
        registry.enableSimpleBroker("/topic");   // Enables a simple in-memory broker


        //   Use this for enabling a Full featured broker like RabbitMQ
        /*
        registry.enableStompBrokerRelay("/topic")
                .setRelayHost("localhost")
                .setRelayPort(61613)
                .setClientLogin("guest")
                .setClientPasscode("guest");
        */
    }
}
