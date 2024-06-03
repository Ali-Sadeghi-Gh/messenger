package ir.mohaymen.messenger.services;

import ir.mohaymen.messenger.dto.MessageRequest;
import ir.mohaymen.messenger.entities.ChatEntity;
import ir.mohaymen.messenger.entities.MessageEntity;
import ir.mohaymen.messenger.entities.UserEntity;
import ir.mohaymen.messenger.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class SendMessageService {
    private final UserRepository userRepository;

    public ResponseEntity<?> sendMessage(MessageRequest request) {
        Long id = request.getReceiverId();
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Receiver id must be an integer");
        }

        if (request.getMessage() == null || request.getMessage().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Message must not be empty");
        }

        Optional<UserEntity> receiverOptional = userRepository.findById(id);
        if (receiverOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Receiver id not found");
        }
        Optional<UserEntity> senderOptional = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        if (senderOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sender not found");
        }

        UserEntity receiver = receiverOptional.get();
        UserEntity sender = senderOptional.get();

        if (receiver.equals(sender)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No self messaging");
        }

        ChatEntity receiverChat = receiver.findChatByAddressee(sender);
        ChatEntity senderChat = sender.findChatByAddressee(receiver);

        if (receiverChat == null) {
            receiverChat = ChatEntity.builder()
                    .unreadCount(0)
                    .addressee(sender)
                    .messages(new ArrayList<>())
                    .build();
            receiver.addChat(receiverChat);
        }

        if (senderChat == null) {
            senderChat = ChatEntity.builder()
                    .unreadCount(0)
                    .addressee(receiver)
                    .messages(new ArrayList<>())
                    .build();
            sender.addChat(senderChat);
        }

        MessageEntity message = MessageEntity.builder()
                .sender(sender)
                .message(request.getMessage())
                .entities(request.getEntities())
                .date(new Date())
                .read(false)
                .build();

        receiverChat.addMessage(message);
        senderChat.addMessage(message);

        userRepository.save(receiver);
        userRepository.save(sender);
        log.info("User with id " + sender.getId() + " sends a message to user with id " + receiver.getId());
        return ResponseEntity.ok().build();
    }
}
