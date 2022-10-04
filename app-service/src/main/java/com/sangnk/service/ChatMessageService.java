package com.sangnk.service;

import com.sangnk.core.entity.ChatMessage;
import com.sangnk.core.entity._enum.MessageStatus;
import com.sangnk.core.exception.ResourceNotFoundException;

import java.util.List;

public interface ChatMessageService {
    ChatMessage save(ChatMessage chatMessage);

    long countNewMessages(String senderId, String recipientId);

    List<ChatMessage> findChatMessages(String senderId, String recipientId);

    ChatMessage findById(Long id) throws ResourceNotFoundException;

    void updateStatuses(String chatId, MessageStatus status);
}
