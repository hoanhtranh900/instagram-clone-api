package com.sangnk.core.repository;

import com.sangnk.core.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    @Query("select m from ChatMessage m where m.chatId = :chatId")
    List<ChatMessage> findByChatId(String chatId);
}
