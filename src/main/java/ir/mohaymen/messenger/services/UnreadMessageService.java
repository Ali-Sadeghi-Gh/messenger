package ir.mohaymen.messenger.services;

import ir.mohaymen.messenger.dto.UnreadMessageResponse;
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
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UnreadMessageService {
    private final UserRepository userRepository;
    private final HtmlConvertor htmlConvertor;

    public ResponseEntity<List<UnreadMessageResponse>> getUnreadMessages(Long addresseeId) {
        Optional<UserEntity> userOptional = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        if (userOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found");
        }
        UserEntity user = userOptional.get();

        Optional<UserEntity> addresseeOptional = userRepository.findById(addresseeId);
        if (addresseeOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Addressee not found");
        }
        UserEntity addressee = addresseeOptional.get();

        ChatEntity chat = user.findChatByAddressee(addressee);


        List<UnreadMessageResponse> unreadMessageResponses = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            MessageEntity unreadMessage = chat.getUnreadMessage();
            if (unreadMessage == null) {
                break;
            }
            unreadMessageResponses.add(UnreadMessageResponse.builder()
                    .html(htmlConvertor.convert(unreadMessage))
                    .build()
            );
        }

        userRepository.save(user);
        log.info("user with id " + user.getId() + " get unread messages of addressee with id " + addresseeId);
        return ResponseEntity.ok(unreadMessageResponses);
    }
}
