package by.lwo.ukis.service;

import by.lwo.ukis.dto.UserMessagesDto;
import by.lwo.ukis.model.UserMessages;
import by.lwo.ukis.model.enums.MessagesStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UsersMessagesService {
    Page<UserMessages> findAllMessagesBySenderAndRecipientId(Long senderId, Long recipientId, Pageable pageable);

    UserMessages saveUserMessage(UserMessagesDto userMessagesDto);

    UserMessages findMessageById(Long id);

    UserMessages updateUserMessage(UserMessagesDto userMessagesDto);

    UserMessages updateUserMessageStatus(MessagesStatus status, UserMessages userMessages);

    void deleteUserMessage(UserMessages userMessage);
}
