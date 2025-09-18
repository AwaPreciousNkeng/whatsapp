package com.codewithpcodes.whatsapp.message;

import java.time.LocalDateTime;

public record MessageResponse(
        Long id,
        String content,
        MessageType type,
        MessageState state,
        String senderId,
        String receiverId,
        LocalDateTime createdAt,
        byte[] media
) {
}
