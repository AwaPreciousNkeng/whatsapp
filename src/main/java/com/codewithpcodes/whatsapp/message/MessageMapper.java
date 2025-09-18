package com.codewithpcodes.whatsapp.message;

import org.springframework.stereotype.Service;

@Service
public class MessageMapper {
    public MessageResponse toMessageResponse(Message message) {
        return new MessageResponse(
                message.getId(),
                message.getContent(),
                message.getSenderId(),
                message.getReceiverId(),
                message.getType(),
                message.getState(),
                message.getCreatedDate(),
                //TODO read the media file
        )
    }
}
