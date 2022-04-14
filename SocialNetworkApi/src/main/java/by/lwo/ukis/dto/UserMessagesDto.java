package by.lwo.ukis.dto;

import by.lwo.ukis.model.UserMessages;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserMessagesDto {
    private Long id;
    private Long recipientId;
    private Long senderId;
    private String value;
    private Date created;
    private Date updated;
    private String messageStatus;

    public static UserMessagesDto fromUserMessages(UserMessages userMessages) {
        UserMessagesDto userMessagesDto = new UserMessagesDto();
        userMessagesDto.setId(userMessages.getId());
        userMessagesDto.setSenderId(userMessages.getUser().getId());
        userMessagesDto.setRecipientId(userMessages.getRecipientId());
        userMessagesDto.setValue(userMessages.getValue());
        userMessagesDto.setCreated(userMessages.getCreated());
        userMessagesDto.setUpdated(userMessages.getUpdated());
        userMessagesDto.setMessageStatus(userMessages.getMessagesStatus().name());

        return userMessagesDto;
    }

    public UserMessages toUserMessage(){
        UserMessages userMessages = new UserMessages();
        userMessages.setRecipientId(recipientId);
        userMessages.setValue(value);
        return userMessages;
    }
}
