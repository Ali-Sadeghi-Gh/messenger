package ir.mohaymen.messenger.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "chat_tb")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ChatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    private UserEntity addressee;
    @ManyToMany(cascade=CascadeType.ALL)
    private List<MessageEntity> messages;
    @Column(name = "unread_count", nullable = false)
    private Integer unreadCount;

    @Override
    public boolean equals(Object o) {
        if (!ChatEntity.class.equals(o.getClass())) {
            return false;
        }
        ChatEntity chat = (ChatEntity) o;
        return addressee == chat.addressee;
    }

    public void addMessage(MessageEntity message) {
        if (addressee.equals(message.getSender())) {
            unreadCount++;
        }
        messages.add(message);
    }
}
