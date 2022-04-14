package by.lwo.ukis.dto;

import by.lwo.ukis.model.Friends;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserFriendDto {

    private Long id;
    private Long userId;
    private Long friendId;
    private String friendStatus;

    public static UserFriendDto fromFriends(Friends friends) {
        UserFriendDto userFriendDto = new UserFriendDto();
        userFriendDto.setId(friends.getId());
        userFriendDto.setUserId(friends.getUser().getId());
        userFriendDto.setFriendId(friends.getFriendId());
        userFriendDto.setFriendStatus(friends.getFriendsStatus().name());

        return userFriendDto;
    }


}
