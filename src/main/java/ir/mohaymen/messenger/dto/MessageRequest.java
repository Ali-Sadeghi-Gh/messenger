package ir.mohaymen.messenger.dto;

import ir.mohaymen.messenger.entities.EntityEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageRequest {
    private String message;
    private Long receiverId;
    private List<EntityEntity> entities;
}
