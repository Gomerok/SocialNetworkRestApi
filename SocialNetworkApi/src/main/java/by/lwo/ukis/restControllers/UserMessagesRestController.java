package by.lwo.ukis.restControllers;

import by.lwo.ukis.dto.UserMessagesDto;
import by.lwo.ukis.model.User;
import by.lwo.ukis.model.UserMessages;
import by.lwo.ukis.model.enums.MessagesStatus;
import by.lwo.ukis.service.UserService;
import by.lwo.ukis.service.UsersMessagesService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/users/messages")
public class UserMessagesRestController {

    private final UserService userService;
    private final UsersMessagesService usersMessagesService;

    @Autowired
    public UserMessagesRestController(UserService userService, UsersMessagesService usersMessagesService) {
        this.userService = userService;
        this.usersMessagesService = usersMessagesService;
    }

    @GetMapping(value = "/messages/")
    public ResponseEntity<Object> getAllMessagesBySenderAndRecipientId(@RequestParam(name = "recipientId", required = true) String recipientId,
                                                                       @RequestParam(name = "pageNo", required = false, defaultValue = "0") String pageNo,
                                                                       @RequestParam(name = "pageSize", required = false, defaultValue = "2") String pageSize,
                                                                       Authentication authentication) {
        try {

            String senderName = authentication.getName();
            User recipient = userService.findById(Long.parseLong(recipientId));
            User sender = userService.findByUsername(senderName);

            if (!NumberUtils.isNumber(recipientId) || !NumberUtils.isNumber(pageNo) || !NumberUtils.isNumber(pageSize)) {
                return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
            }

            if (recipient == null) {
                return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
            }

            Pageable pageable = PageRequest.of(Integer.parseInt(pageNo), Integer.parseInt(pageSize));
            Page<UserMessages> userMessagesPage = usersMessagesService.findAllMessagesBySenderAndRecipientId(sender.getId(), recipient.getId(), pageable);

            int totalElements = (int) userMessagesPage.getTotalElements();
            Page<UserMessagesDto> userMessagesDtoPage = new PageImpl<UserMessagesDto>(userMessagesPage.getContent()
                    .stream()
                    .map(message -> UserMessagesDto.fromUserMessages(message))
                    .collect(Collectors.toList()), pageable, totalElements);

            List<UserMessagesDto> resultList = userMessagesDtoPage.getContent();

            if (resultList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<Object>(resultList, HttpStatus.OK);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/messages/{id}")
    public ResponseEntity<Object> getUserMessageById(@PathVariable(name = "id") Long id) {
        try {
            UserMessages userMessage = usersMessagesService.findMessageById(id);

            if (userMessage == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            UserMessagesDto result = UserMessagesDto.fromUserMessages(userMessage);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/messages/")
    public ResponseEntity<Object> saveUserMessages(@RequestBody UserMessagesDto userMessagesDto, Authentication authentication) {
        try {

            String senderName = authentication.getName();
            User sender = userService.findByUsername(senderName);
            userMessagesDto.setSenderId(sender.getId());

            if (userService.findById(userMessagesDto.getRecipientId()) != null) {
                UserMessages savedUserMessages = usersMessagesService.saveUserMessage(userMessagesDto);
                UserMessagesDto result = UserMessagesDto.fromUserMessages(savedUserMessages);
                return new ResponseEntity<Object>(result, HttpStatus.OK);
            } else {
                return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
            }

        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/messages/{id}")
    public ResponseEntity<Object> updateUserMessage(@PathVariable("id") Long id, @RequestBody UserMessagesDto userMessagesDto, Authentication authentication) {
        try {
            String bearerName = authentication.getName();
            User bearerUser = userService.findByUsername(bearerName);

            UserMessages userMessage= usersMessagesService.findMessageById(id);
            if (userMessage != null) {
                if (bearerUser.getId().equals(userMessage.getUser().getId())) {
                    userMessagesDto.setId(id);
                    UserMessages updatedUserMessage = usersMessagesService.updateUserMessage(userMessagesDto);
                    UserMessagesDto result = UserMessagesDto.fromUserMessages(updatedUserMessage);
                    return new ResponseEntity<Object>(result, HttpStatus.OK);
                } else {
                    return new ResponseEntity<Object>(HttpStatus.FORBIDDEN);
                }
            } else {
                return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/messages/status/{id}")
    public ResponseEntity<Object> updateUserMessageStatus(@PathVariable("id") Long id, @RequestBody String status) {
        try {
            UserMessages userMessages = usersMessagesService.findMessageById(id);

            boolean isEnum = false;
            for (MessagesStatus e : MessagesStatus.values()) {
                if (status.equals(e.toString())) {
                    isEnum = true;
                }
            }
            if (!isEnum) {
                return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
            }
            if (userMessages != null) {
                UserMessages updatedUser = usersMessagesService.updateUserMessageStatus(MessagesStatus.valueOf(status), userMessages);
                UserMessagesDto result = UserMessagesDto.fromUserMessages(updatedUser);
                return new ResponseEntity<Object>(result, HttpStatus.OK);
            } else {
                return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/messages/{id}")
    public ResponseEntity<HttpStatus> deleteUserMessage(@PathVariable("id") Long id) {
        try {
            UserMessages userMessage = usersMessagesService.findMessageById(id);
            if (userMessage != null) {
                usersMessagesService.deleteUserMessage(userMessage);
                return new ResponseEntity<HttpStatus>(HttpStatus.OK);
            } else {
                return new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
        }
    }
}
