package ir.mohaymen.messenger.services;

import ir.mohaymen.messenger.dto.MessageRequest;
import ir.mohaymen.messenger.entities.MessageEntity;
import ir.mohaymen.messenger.entities.UserEntity;
import ir.mohaymen.messenger.repositories.MessageRepository;
import ir.mohaymen.messenger.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class SendMessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public ResponseEntity<?> sendMessage(MessageRequest request) {
        Optional<UserEntity> receiverOptional = userRepository.findById(request.getReceiverId());
        if (receiverOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Receiver id not found");
        }
        Optional<UserEntity> senderOptional = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        if (senderOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sender not found");
        }
        MessageEntity message = MessageEntity.builder()
                .message(request.getMessage())
                .entities(request.getEntities())
                .receiver(receiverOptional.get())
                .sender(senderOptional.get())
                .date(new Date())
                .build();
        messageRepository.save(message);
        return ResponseEntity.ok().build();
    }
}
