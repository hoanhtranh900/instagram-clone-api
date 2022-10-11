package com.sangnk.service.impl;

import com.sangnk.core.entity.ChatMessage;
import com.sangnk.core.entity._enum.MessageStatus;
import com.sangnk.core.exception.ResourceNotFoundException;
import com.sangnk.core.repository.ChatMessageRepository;
import com.sangnk.service.ChatMessageService;
import com.sangnk.service.ChatRoomService;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChatMessageServiceImpl implements ChatMessageService {

    @Autowired
    private ChatMessageRepository repository;
    @Autowired
    private ChatRoomService chatRoomService;

    @Override
    public ChatMessage save(ChatMessage chatMessage) {
        chatMessage.setStatus(MessageStatus.RECEIVED);
        repository.save(chatMessage);
        return chatMessage;
    }

    @Override
    public long countNewMessages(String senderId, String recipientId) {
//        return repository.countBySenderIdAndRecipientIdAndStatus(
//                senderId, recipientId, MessageStatus.RECEIVED);
        return 0;
    }

    @Override
    public List<ChatMessage> findChatMessages(String senderId, String recipientId) {
        Optional<String> chatId = chatRoomService.getChatId(senderId, recipientId, false);
        if (chatId.isPresent()) {
            List<ChatMessage> messages = repository.findByChatId(chatId.get());
            if (messages.size() > 0) {
                //update status to delivered
                repository.saveAll(messages.stream().map(message -> {
                    message.setStatus(MessageStatus.DELIVERED);
                    return message;
                }).collect(Collectors.toList()));
            }
            return messages;
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public ChatMessage findById(Long id) throws ResourceNotFoundException {
//        return repository
//                .findById(id)
//                .map(chatMessage -> {
//                    chatMessage.setStatus(MessageStatus.DELIVERED);
//                    return repository.save(chatMessage);
//                })
//                .orElseThrow(() ->
//                        new ResourceNotFoundException("can't find message (" + id + ")"));

        ChatMessage chatMessage = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("can't find message (" + id + ")"));
        chatMessage.setStatus(MessageStatus.DELIVERED);
        return repository.save(chatMessage);
    }

    @Override
    public void updateStatuses(String chatId, MessageStatus status) {

    }
}
