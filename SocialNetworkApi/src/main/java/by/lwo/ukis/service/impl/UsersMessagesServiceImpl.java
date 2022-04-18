package by.lwo.ukis.service.impl;

import by.lwo.ukis.dto.UserMessagesDto;
import by.lwo.ukis.model.UserMessages;
import by.lwo.ukis.model.enums.MessagesStatus;
import by.lwo.ukis.repository.UsersMessagesRepository;
import by.lwo.ukis.service.UserService;
import by.lwo.ukis.service.UsersMessagesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class UsersMessagesServiceImpl implements UsersMessagesService {

    private final UsersMessagesRepository usersMessagesRepository;

    private final UserService userService;

    @Autowired
    public UsersMessagesServiceImpl(UsersMessagesRepository usersMessagesRepository, UserService userService) {
        this.usersMessagesRepository = usersMessagesRepository;
        this.userService = userService;
    }

    @Override
    public Page<UserMessages> findAllMessagesBySenderAndRecipientId(Long senderId, Long recipientId, Pageable pageable) {
        Page<UserMessages> result = usersMessagesRepository.findAllBySenderAndRecipientIdPagination(senderId, recipientId, pageable);
        log.info("IN findAllMessagesBySenderAndRecipientId - found {} page", result.getTotalElements());
        return result;
    }

    @Override
    public UserMessages saveUserMessage(UserMessagesDto userMessagesDto) {
        UserMessages userMessages = userMessagesDto.toUserMessage();

        userMessages.setUser(userService.findById(userMessagesDto.getSenderId()));
        userMessages.setMessagesStatus(MessagesStatus.CREATED);

        UserMessages savedUserMessages = usersMessagesRepository.save(userMessages);
        log.info("IN saveUserMessage - message: {} successfully saved", savedUserMessages);
        return savedUserMessages;
    }

    @Override
    public UserMessages findMessageById(Long id) {
        UserMessages result = usersMessagesRepository.findById(id).orElse(null);

        if (result == null) {
            log.warn("IN findMessageById - no message found by id: {}", id);
            return null;
        }

        log.info("IN findMessageById - message: {} found by id: {}", result, id);
        return result;
    }

    @Override
    public UserMessages updateUserMessage(UserMessagesDto userMessagesDto) {
        UserMessages updatedUserMessages = findMessageById(userMessagesDto.getId());
        if (userMessagesDto.getValue() != null) {
            updatedUserMessages.setValue(userMessagesDto.getValue());
            updatedUserMessages.setMessagesStatus(MessagesStatus.UPDATED);
        }
        UserMessages result = usersMessagesRepository.save(updatedUserMessages);
        log.info("IN updateUserMessage - userMessage with id: {} successfully update", result.getId());
        return result;
    }

    @Override
    public UserMessages updateUserMessageStatus(MessagesStatus status, UserMessages userMessages) {
        userMessages.setMessagesStatus(status);
        UserMessages result = usersMessagesRepository.save(userMessages);
        log.info("IN updateUserMessageStatus - userMessage with id: {} successfully update status '{}' ", result.getId(), status.name());
        return result;
    }

    @Override
    public void deleteUserMessage(UserMessages userMessage) {
//        userMessage.setMessagesStatus(MessagesStatus.DELETED);
//        UserMessages result = usersMessagesRepository.save(userMessage);
        usersMessagesRepository.deleteMessage(userMessage.getId());
        log.info("IN deleteUserMessage - message with id: {} successfully deleted", userMessage.getId());
    }
}
