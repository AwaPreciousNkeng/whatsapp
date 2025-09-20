package com.codewithpcodes.whatsapp.message;

import com.codewithpcodes.whatsapp.file.FileUtils;
import org.springframework.stereotype.Service;

@Service
public class MessageMapper {
    public MessageResponse toMessageResponse(Message message) {
        return new MessageResponse(
                message.getId(),
                message.getContent(),
                message.getType(),
                message.getState(),
                message.getSenderId(),
                message.getReceiverId(),
                message.getCreatedDate(),
                FileUtils.readFileFromLocation(message.getMediaFilePath())
        );
    }
}
