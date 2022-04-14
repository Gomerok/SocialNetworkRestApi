package by.lwo.ukis.service;

import by.lwo.ukis.dto.UserFriendDto;
import by.lwo.ukis.model.Friends;
import by.lwo.ukis.model.enums.FriendsStatus;
import by.lwo.ukis.model.User;

import java.util.List;

public interface FriendsService {
    List<Friends> findAllFriendsByUserId(Long userId);

    List<Friends> findFriendByUserAndFriendId(Long userId, Long friendId);

    Friends saveFriend(UserFriendDto friends);

    Friends updateFriendStatus(FriendsStatus status, User user, Long friendId);

    Friends findFriendById(Long id);
}
