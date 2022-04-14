package by.lwo.ukis.dto;

import by.lwo.ukis.model.UserMessages;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserMessagesDto {

    @JsonIgnore
    private Long id;
    private Long recipientId;
    private Long senderId;
    @Pattern(regexp = "^[a-zA-Z0-9,.()!?]{3,500}$", message = "Incorrect value")
    private String value;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date created;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date updated;
    @Pattern(regexp = "^(CREATED|DELETED|UPDATED)$", message = "Incorrect newsStatus")
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

    @JsonProperty("id")
    public Long getId() {
        return id;
    }
    @JsonIgnore
    public void setId(Long id) {
        this.id = id;
    }
}
