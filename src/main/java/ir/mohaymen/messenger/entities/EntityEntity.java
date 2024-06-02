package ir.mohaymen.messenger.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "entity_tb")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class EntityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Enumerated(value = EnumType.STRING)
    private EntityType type;
    @Column(name = "url")
    private String url;
    private Integer entityOffset;
    private Integer entityLength;
}
