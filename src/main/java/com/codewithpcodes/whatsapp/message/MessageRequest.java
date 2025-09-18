package com.codewithpcodes.whatsapp.message;

public record MessageRequest(
        String content,
        String senderId,
        String receiverId,
        MessageType type,
        String chatId
) {
}
