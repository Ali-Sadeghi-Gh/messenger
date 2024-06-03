package ir.mohaymen.messenger.services;

import ir.mohaymen.messenger.dto.ChatResponse;
import ir.mohaymen.messenger.entities.ChatEntity;
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
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatService {
    private final UserRepository userRepository;

    public ResponseEntity<List<ChatResponse>> getChats() {
        Optional<UserEntity> userOptional = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        if (userOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found");
        }
        UserEntity user = userOptional.get();
        List<ChatEntity> chats = user.getChats();
        List<ChatResponse> chatResponses = new ArrayList<>();
        for (ChatEntity chat : chats) {
            chatResponses.add(ChatResponse.builder()
                    .addresseeId(chat.getAddressee().getId())
                    .addresseeName(chat.getAddressee().getName())
                    .unreadCount(chat.getUnreadCount())
                    .build()
            );
        }
        log.info("User with id " + user.getId() + " get his chats");
        return ResponseEntity.ok(chatResponses);
    }
}
