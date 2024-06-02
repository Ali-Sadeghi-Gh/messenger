package ir.mohaymen.messenger.controllers;

import ir.mohaymen.messenger.dto.MessageRequest;
import ir.mohaymen.messenger.services.SendMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api")
@RequiredArgsConstructor
public class MessageController {
    private final SendMessageService sendMessageService;

    @PostMapping(path = "sendMessage")
    public ResponseEntity<?> sendMessage(@RequestBody MessageRequest request) {
        return sendMessageService.sendMessage(request);
    }

}
