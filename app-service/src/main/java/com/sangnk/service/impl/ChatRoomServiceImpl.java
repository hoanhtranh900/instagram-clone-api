package com.sangnk.service.impl;

import com.sangnk.core.entity.ChatRoom;
import com.sangnk.core.repository.ChatRoomRepository;
import com.sangnk.service.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChatRoomServiceImpl implements ChatRoomService {
    @Autowired private ChatRoomRepository chatRoomRepository;
    @Override
    public Optional<String> getChatId(String senderId, String recipientId, boolean createIfNotExist) {
//        return chatRoomRepository
//                .findBySenderIdAndRecipientId(senderId, recipientId)
//                .map(ChatRoom::getChatId)
//                .or(() -> {
//                    if(!createIfNotExist) {
//                        return  Optional.empty();
//                    }
//                    var chatId =
//                            String.format("%s_%s", senderId, recipientId);
//
//                    ChatRoom senderRecipient = ChatRoom
//                            .builder()
//                            .chatId(chatId)
//                            .senderId(senderId)
//                            .recipientId(recipientId)
//                            .build();
//
//                    ChatRoom recipientSender = ChatRoom
//                            .builder()
//                            .chatId(chatId)
//                            .senderId(recipientId)
//                            .recipientId(senderId)
//                            .build();
//                    chatRoomRepository.save(senderRecipient);
//                    chatRoomRepository.save(recipientSender);
//
//                    return Optional.of(chatId);
//                });


        //find by senderId and recipientId. if not found, create new chat room with chatId = senderId_recipientId
        Optional<ChatRoom> chatRoom = chatRoomRepository.findBySenderIdAndRecipientId(senderId, recipientId);
        if(chatRoom.isPresent()) {
            return chatRoom.map(ChatRoom::getChatId);
        } else {
            if(!createIfNotExist) {
                return Optional.empty();
            }
            String chatId = String.format("%s_%s", senderId, recipientId);
            ChatRoom senderRecipient = ChatRoom.builder().chatId(chatId).senderId(senderId).recipientId(recipientId).build();
            ChatRoom recipientSender = ChatRoom.builder().chatId(chatId).senderId(recipientId).recipientId(senderId).build();
            chatRoomRepository.save(senderRecipient);
            chatRoomRepository.save(recipientSender);
            return Optional.of(chatId);
        }

    }
}
