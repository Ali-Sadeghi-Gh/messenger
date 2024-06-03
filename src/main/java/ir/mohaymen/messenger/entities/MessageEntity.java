package ir.mohaymen.messenger.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
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
    @ManyToMany(cascade=CascadeType.ALL)
    private List<EntityEntity> entities = new ArrayList<>();
    @Column(name = "date")
    private Date date;
    @Column(name = "read")
    private boolean read;
    @OneToOne(cascade = CascadeType.ALL)
    private UserEntity sender;
}
