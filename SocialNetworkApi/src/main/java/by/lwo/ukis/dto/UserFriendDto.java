package by.lwo.ukis.dto;

import by.lwo.ukis.model.Friends;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserFriendDto {

    @JsonIgnore
    private Long id;
    private Long userId;
    private Long friendId;
    @Pattern(regexp = "^(SUBSCRIBER|FRIEND|DELETED)$", message = "Incorrect status")
    private String friendStatus;

    public static UserFriendDto fromFriends(Friends friends) {
        UserFriendDto userFriendDto = new UserFriendDto();
        userFriendDto.setId(friends.getId());
        userFriendDto.setUserId(friends.getUser().getId());
        userFriendDto.setFriendId(friends.getFriendId());
        userFriendDto.setFriendStatus(friends.getFriendsStatus().name());

        return userFriendDto;
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
