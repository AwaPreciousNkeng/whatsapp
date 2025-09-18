package com.codewithpcodes.whatsapp.message;

import com.codewithpcodes.whatsapp.chat.Chat;
import com.codewithpcodes.whatsapp.chat.ChatRepository;
import com.codewithpcodes.whatsapp.file.FileService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository repository;
    private final MessageMapper mapper;
    private final ChatRepository chatRepository;
    private final FileService fileService;

    public void saveMessage(MessageRequest request) {
        Chat chat = chatRepository.findById(request.chatId())
                .orElseThrow(() -> new EntityNotFoundException("Chat with id " + request.chatId() + " not found"));
        Message message = new Message();
        message.setContent(request.content());
        message.setChat(chat);
        message.setSenderId(request.senderId());
        message.setReceiverId(request.receiverId());
        message.setType(request.type());
        message.setState(MessageState.SENT);

        repository.save(message);
        //TODO notification
    }

    public List<MessageResponse> findChatMessages(String chatId) {
        return repository.findMessagesByChatId(chatId)
                .stream()
                .map(mapper::toMessageResponse)
                .toList();
    }

    @Transactional
    public void setMessagesToSeen(String chatId, Authentication authentication) {
        Chat chat = chatRepository.findById(chatId).orElseThrow(() -> new EntityNotFoundException("Chat with id " + chatId + " not found"));
//        final String recipientId = getRecipientId(chat, authentication);authentication
        repository.setMessagesToSeenByChatId(chatId, MessageState.SEEN);
        //TODO send notification
    }

    public void uploadMediaMessage(String chatId, MultipartFile file, Authentication authentication) {
        Chat chat = chatRepository.findById(chatId).orElseThrow(() -> new EntityNotFoundException("Chat with id " + chatId + " not found"));

        final String senderId =  getSenderId(chat, authentication);
        final String recipientId = getRecipientId(chat, authentication);

        final String filePath = fileService.saveFile(file, senderId);
        Message message = new Message();
        message.setChat(chat);
        message.setSenderId(senderId);
        message.setReceiverId(recipientId);
        message.setType(MessageType.IMAGE);
        message.setState(MessageState.SENT);
        message.setMediaFilePath(filePath);
        repository.save(message);
        //TODO notification
    }

    private String getSenderId(Chat chat, Authentication authentication) {
        if (chat.getSender().getId().equals(authentication.getName())) {
            return chat.getSender().getId();
        }
        return chat.getRecipient().getId();
    }

    private String getRecipientId(Chat chat, Authentication authentication) {
        if (chat.getSender().getId().equals(authentication.getName())) {
            return chat.getRecipient().getId();
        }
        return chat.getSender().getId();
    }
}
