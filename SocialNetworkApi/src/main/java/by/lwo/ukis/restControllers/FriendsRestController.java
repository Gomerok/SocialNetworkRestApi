package by.lwo.ukis.restControllers;

import by.lwo.ukis.dto.AdminUserDto;
import by.lwo.ukis.dto.UserFriendDto;
import by.lwo.ukis.model.Friends;
import by.lwo.ukis.model.enums.FriendsStatus;
import by.lwo.ukis.model.User;
import by.lwo.ukis.service.FriendsService;
import by.lwo.ukis.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/users/friends")
public class FriendsRestController {

    private final UserService userService;
    private final FriendsService friendsService;

    @Autowired
    public FriendsRestController(UserService userService, FriendsService friendsService) {
        this.userService = userService;
        this.friendsService = friendsService;
    }

    @GetMapping("/")
    public ResponseEntity<Object> getAllFriends(Authentication authentication) {
        try {
            String bearerName = authentication.getName();
            User user = userService.findByUsername(bearerName);

            List<Friends> userFriends = friendsService.findAllFriendsByUserId(user.getId());

            if (userFriends.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            List<UserFriendDto> result = new ArrayList<>();

            for (Friends friend : userFriends) {
                result.add(UserFriendDto.fromFriends(friend));
            }
            return new ResponseEntity<Object>(result, HttpStatus.OK);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable(name = "id") Long id) {
        try {
            Friends friends = friendsService.findFriendById(id);

            if (friends == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            UserFriendDto result = UserFriendDto.fromFriends(friends);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/")
    public ResponseEntity<Object> saveFriend(@RequestBody UserFriendDto userFriendDto, Authentication authentication) {
        try {
            String bearerName = authentication.getName();
            User user = userService.findByUsername(bearerName);

            if (!user.getId().equals(userFriendDto.getUserId())) {
                return new ResponseEntity<Object>(HttpStatus.FORBIDDEN);
            }

            List<Friends> userFriends = friendsService.findFriendByUserAndFriendId(user.getId(), userFriendDto.getFriendId());

            if (userFriends.isEmpty()) {
                Friends savedFriend = friendsService.saveFriend(userFriendDto);
                UserFriendDto result = UserFriendDto.fromFriends(savedFriend);
                return new ResponseEntity<Object>(result, HttpStatus.OK);
            } else {
                return new ResponseEntity<Object>(HttpStatus.FOUND);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/status/{friendId}")
    public ResponseEntity<Object> updateUserFriendStatus(@PathVariable("friendId") Long friendId, @RequestBody String status, Authentication authentication) {
        try {
            String bearerName = authentication.getName();
            User isUser = userService.findById(friendId);
            User bearerUser = userService.findByUsername(bearerName);

            boolean isEnum = false;
            for (FriendsStatus e : FriendsStatus.values()) {
                if (status.equals(e.toString())) {
                    isEnum = true;
                }
            }
            if (!isEnum) {
                return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
            }

            if (friendsService.findFriendByUserAndFriendId(bearerUser.getId(), friendId).isEmpty()) {
                return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
            }

            if (isUser != null) {
                Friends updatedFriend = friendsService.updateFriendStatus(FriendsStatus.valueOf(status), bearerUser, isUser.getId());
                UserFriendDto result = UserFriendDto.fromFriends(updatedFriend);
                return new ResponseEntity<Object>(result, HttpStatus.OK);
            } else {
                return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
            }

        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
        }
    }
}



