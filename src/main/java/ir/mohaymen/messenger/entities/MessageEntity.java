package ir.mohaymen.messenger.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "message_tb")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class MessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "message")
    private String message;
    @OneToOne(cascade=CascadeType.ALL)
    private UserEntity sender;
    @OneToOne(cascade=CascadeType.ALL)
    private UserEntity receiver;
    @ManyToMany(cascade=CascadeType.ALL)
    private List<EntityEntity> entities = new ArrayList<>();
}
