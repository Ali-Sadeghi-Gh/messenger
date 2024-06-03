package ir.mohaymen.messenger.controllers;

import ir.mohaymen.messenger.dto.ChatResponse;
import ir.mohaymen.messenger.dto.MessageRequest;
import ir.mohaymen.messenger.dto.UnreadMessageResponse;
import ir.mohaymen.messenger.services.ChatService;
import ir.mohaymen.messenger.services.SendMessageService;
import ir.mohaymen.messenger.services.UnreadMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api")
@RequiredArgsConstructor
public class MessageController {
    private final SendMessageService sendMessageService;
    private final ChatService chatService;
    private final UnreadMessageService unreadMessageService;

    @PostMapping(path = "sendMessage")
    public ResponseEntity<?> sendMessage(@RequestBody MessageRequest request) {
        return sendMessageService.sendMessage(request);
    }

    @GetMapping(path = "getChats")
    public ResponseEntity<List<ChatResponse>> getChats() {
        return chatService.getChats();
    }

    @GetMapping(path = "getUnreadMessages/{addresseeId}")
    public ResponseEntity<List<UnreadMessageResponse>> getUnreadMessages(@PathVariable Long addresseeId) {
        return unreadMessageService.getUnreadMessages(addresseeId);
    }
}
