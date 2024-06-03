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
    private UserEntity receiver;
    @ManyToMany(cascade=CascadeType.ALL)
    private List<MessageEntity> messages = new ArrayList<>();
}
