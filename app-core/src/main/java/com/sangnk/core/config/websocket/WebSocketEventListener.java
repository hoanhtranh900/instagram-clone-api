package com.sangnk.core.config.websocket;

import com.dgtt.core.entity.Bidder;
import com.dgtt.core.model.bidder.ChatMessage;
import com.dgtt.core.repository.BidderRepository;
import com.dgtt.core.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketEventListener {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;
    @Autowired
    private UserRepository userRepository;
    @Autowired private BidderRepository bidderRepository;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        logger.info("Received a new web socket connection");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        Long userId = (Long) headerAccessor.getSessionAttributes().get("userId");
        Long bidderId = (Long) headerAccessor.getSessionAttributes().get("bidderId");
        Long productId = (Long) headerAccessor.getSessionAttributes().get("productId");
        if (userId != null && bidderId != null && productId != null) {
            Bidder bidder = bidderRepository.findById(bidderId).orElse(null);
            if (bidder != null) {

                bidder.setOnlineStatus(false);
                bidderRepository.save(bidder);

                /* socket OFFLINE */
                ChatMessage chatMessage = new ChatMessage();
                chatMessage.setType(ChatMessage.MessageType.OFFLINE);
                chatMessage.setSender(bidder.getUser().getUserName());
                chatMessage.setUserId(userId);
                messagingTemplate.convertAndSend("/topic/group/bargain-product", chatMessage);
            }
        }
    }
}
