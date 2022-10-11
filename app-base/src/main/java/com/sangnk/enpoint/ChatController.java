package com.sangnk.enpoint;

import com.sangnk.core.dto.modal.ChatNotification;
import com.sangnk.core.dto.response.ResponseData;
import com.sangnk.core.entity.AdmUser;
import com.sangnk.core.entity.ChatMessage;
import com.sangnk.core.exception.BaseException;
import com.sangnk.core.exception.ResourceNotFoundException;
import com.sangnk.core.exception.Result;
import com.sangnk.core.utils.UtilsCommon;
import com.sangnk.service.AdmUserService;
import com.sangnk.service.ChatMessageService;
import com.sangnk.service.ChatRoomService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.Optional;

@Controller
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
public class ChatController {

    @Autowired private SimpMessagingTemplate messagingTemplate;
    @Autowired private ChatMessageService chatMessageService;
    @Autowired private ChatRoomService chatRoomService;

    @Autowired private AdmUserService<AdmUser> admUserService;

    @MessageMapping("/chat")
    public void processMessage(@Payload ChatMessage chatMessage) {
        //chatId sẽ tự động tạo nếu khng tồn tại từ đó tìm được chatRoom
        Optional<String> chatId = chatRoomService
                .getChatId(chatMessage.getSenderId(), chatMessage.getRecipientId(), true);
        chatMessage.setChatId(chatId.get());
        chatMessage.setTimestamp(new Date());
        chatMessage.setSenderName(admUserService.get(Long.valueOf(chatMessage.getSenderId())).get().getFullName());
        chatMessage.setRecipientName(admUserService.get(Long.valueOf(chatMessage.getRecipientId())).get().getFullName());
        ChatMessage saved = chatMessageService.save(chatMessage);
        messagingTemplate.convertAndSendToUser(
                chatMessage.getRecipientId(),"/queue/messages",
                new ChatNotification(
                        saved.getId(),
                        saved.getSenderId(),
                        saved.getSenderName(),
                        saved.getContent()));

        //recipient is: /user/ + recipientId + /queue/messages
    }

    //Lấy tin nhắn mới khi click detail cuộc trò chuyện
    @GetMapping("/messages/{senderId}/{recipientId}/count")
    public ResponseEntity<Long> countNewMessages(
            @PathVariable String senderId,
            @PathVariable String recipientId) {

        return ResponseEntity
                .ok(chatMessageService.countNewMessages(senderId, recipientId));
    }

    //Load data cuộc trò chuyện
    @GetMapping("/messages/{senderId}/{recipientId}")
    public ResponseEntity<?> findChatMessages ( @PathVariable String senderId,
                                                @PathVariable String recipientId) {
        return new ResponseEntity<>(new ResponseData<>(chatMessageService.findChatMessages(senderId, recipientId), Result.SUCCESS), HttpStatus.OK);

//        return new ResponseEntity<>(chatMessageService.findChatMessages(senderId, recipientId), HttpStatus.OK);
    }

    @GetMapping("/messages/{id}")
    public ResponseEntity<?> findMessage ( @PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity
                .ok(chatMessageService.findById(id));
    }
}
