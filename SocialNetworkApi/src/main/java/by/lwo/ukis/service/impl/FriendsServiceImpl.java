package by.lwo.ukis.service.impl;

import by.lwo.ukis.dto.UserFriendDto;
import by.lwo.ukis.model.*;
import by.lwo.ukis.model.enums.FriendsStatus;
import by.lwo.ukis.repository.FriendsRepository;
import by.lwo.ukis.repository.UserRepository;
import by.lwo.ukis.service.FriendsService;
import by.lwo.ukis.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class FriendsServiceImpl implements FriendsService {

    private final UserService userService;
    private final FriendsRepository friendsRepository;
    private final UserRepository userRepository;

    @Autowired
    public FriendsServiceImpl(UserService userService, FriendsRepository friendsRepository, UserRepository userRepository) {
        this.userService = userService;
        this.friendsRepository = friendsRepository;
        this.userRepository = userRepository;
    }

//    @Override
//    public List<Friends> findAllFriendsByUserId(Long userId) {
//        List<Friends> result = friendsRepository.getAllFriendsByUserId(userId);
//        log.info("IN getAllFriends - {} users friends found", result.size());
//        return result;
//    }
//
//    @Override
//    public Friends findFriendByUserAndFriendId(Long userId, Long friendId) {
//        Friends result = friendsRepository.getFriendsByUserAndFriendId(userId, friendId);
//        log.info("IN findFriendByUserAndFriendId - {} friend found", result);
//        return result;
//    }
//
//    @Override
//    public Friends saveFriend(UserFriendDto userFriendDto) {
//        Friends newFriend = new Friends();
//        User user = userService.findById(userFriendDto.getUserId());
//        newFriend.setUser(user);
//        newFriend.setFriendId(userFriendDto.getFriendId());
//        newFriend.setFriendsStatus(FriendsStatus.FRIEND);
//
//        Friends results = friendsRepository.save(newFriend);
//        log.info("IN saveFriend - friends: {} successfully saved", results);
//        return results;
//    }
//
//    @Override
//    public Friends updateFriendStatus(FriendsStatus status, User user, Long friendId) {
//        Friends userFriend = findFriendByUserAndFriendId(user.getId(), friendId);
//        userFriend.setFriendsStatus(status);
//        Friends result = friendsRepository.save(userFriend);
//        log.info("IN updateFriendStatus - friends: {} successfully updated", result);
//        return result;
//    }
//
//    @Override
//    public Friends findFriendById(Long id) {
//        Friends result = friendsRepository.findById(id).orElse(null);
//
//        if (result == null) {
//            log.warn("IN findFriendById - no friend found by id: {}", id);
//            return null;
//        }
//
//        log.info("IN findFriendById - friend: {} found by id: {}", result, id);
//        return result;
//    }

    @Override
    public Page<User> findAllFriendsByUserId(Long userId, Pageable pageable) {
        Page<User> result = userRepository.getAllFriendsByUserId(userId , pageable);
        log.info("IN getAllFriends - {} users friends found", result.getTotalElements());
        return result;
    }

    @Override
    public Friends findFriendByUserAndFriendId(Long userId, Long friendId) {
        Friends result = friendsRepository.getFriendsByUserAndFriendId(userId, friendId);
        log.info("IN findFriendByUserAndFriendId - {} friend found", result);
        return result;
    }

    @Override
    public Friends saveFriend(UserFriendDto userFriendDto) {
        Friends newFriend = new Friends();
        User user = userService.findById(userFriendDto.getUserId());
        newFriend.setUser(user);
        newFriend.setFriendId(userFriendDto.getFriendId());
        newFriend.setFriendsStatus(FriendsStatus.FRIEND);

        Friends results = friendsRepository.save(newFriend);
        log.info("IN saveFriend - friends: {} successfully saved", results);
        return results;
    }

    @Override
    public Friends updateFriendStatus(FriendsStatus status, User user, Long friendId) {
        Friends userFriend = findFriendByUserAndFriendId(user.getId(), friendId);
        userFriend.setFriendsStatus(status);
        Friends result = friendsRepository.save(userFriend);
        log.info("IN updateFriendStatus - friends: {} successfully updated", result);
        return result;
    }

    @Override
    public Friends findFriendById(Long id) {
        Friends result = friendsRepository.findById(id).orElse(null);

        if (result == null) {
            log.warn("IN findFriendById - no friend found by id: {}", id);
            return null;
        }

        log.info("IN findFriendById - friend: {} found by id: {}", result, id);
        return result;
    }
}