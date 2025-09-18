package com.codewithpcodes.whatsapp.chat;

import org.springframework.stereotype.Service;

@Service
public class ChatMapper {
    public ChatResponse toChatResponse(Chat chat, String senderId) {
        return new ChatResponse(
                chat.getId(),
                chat.getChatName(senderId),
                chat.getUnreadMessagesCount(senderId),
                chat.getLastMessage(),
                chat.getRecipient().isUserOnline(),
                chat.getSender().getId(),
                chat.getRecipient().getId()
        );
    }
}
