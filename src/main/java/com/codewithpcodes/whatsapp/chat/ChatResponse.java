package com.codewithpcodes.whatsapp.chat;

import java.time.LocalDateTime;

public record ChatResponse(
        String id,
        String name,
        long unreadCount,
        String lastMessage,
        boolean isRecipientOnline,
        String senderId,
        String receiverId
) {
}
