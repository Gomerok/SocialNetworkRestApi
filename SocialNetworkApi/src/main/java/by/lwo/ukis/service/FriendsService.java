package by.lwo.ukis.service;

import by.lwo.ukis.dto.UserFriendDto;
import by.lwo.ukis.model.Friends;
import by.lwo.ukis.model.enums.FriendsStatus;
import by.lwo.ukis.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FriendsService {
//    List<Friends> findAllFriendsByUserId(Long userId);
//
//    Friends findFriendByUserAndFriendId(Long userId, Long friendId);
//
//    Friends saveFriend(UserFriendDto friends);
//
//    Friends updateFriendStatus(FriendsStatus status, User user, Long friendId);
//
//    Friends findFriendById(Long id);

    Page<User> findAllFriendsByUserId(Long userId, Pageable pageable);

    Friends findFriendByUserAndFriendId(Long userId, Long friendId);

    Friends saveFriend(UserFriendDto friends);

    Friends updateFriendStatus(FriendsStatus status, User user, Long friendId);

    Friends findFriendById(Long id);
}
