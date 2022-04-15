package by.lwo.ukis.restControllers;

import by.lwo.ukis.dto.UserFriendDto;
import by.lwo.ukis.model.Friends;
import by.lwo.ukis.model.User;
import by.lwo.ukis.model.enums.FriendsStatus;
import by.lwo.ukis.service.FriendsService;
import by.lwo.ukis.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/users")
public class FriendsRestController {

    private final UserService userService;
    private final FriendsService friendsService;

    @Autowired
    public FriendsRestController(UserService userService, FriendsService friendsService) {
        this.userService = userService;
        this.friendsService = friendsService;
    }

    @GetMapping("/friends")
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

    @GetMapping(value = "/friends/{id}")
    public ResponseEntity<Object> getFriendById(@PathVariable(name = "id") Long id) {
        try {
            Friends friends = friendsService.findFriendById(id);

            if (friends == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            UserFriendDto result = UserFriendDto.fromFriends(friends);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/{friendId}/friends")
    public ResponseEntity<Object> getFriendByUserFriendId(@PathVariable(name = "friendId") Long friendId, Authentication authentication) {
        try {
            Friends friends = friendsService.findFriendByUserAndFriendId(userService.findByUsername(authentication.getName()).getId(), friendId);

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


    @PostMapping("/{friendId}/friends")
    public ResponseEntity<Object> saveFriend(@PathVariable(name = "friendId") Long friendId, @Valid @RequestBody UserFriendDto userFriendDto, Authentication authentication) {
        try {
            String bearerName = authentication.getName();
            User user = userService.findByUsername(bearerName);

            if (userService.findById(friendId) == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            userFriendDto.setFriendId(friendId);
            userFriendDto.setUserId(user.getId());
            Friends userFriends = friendsService.findFriendByUserAndFriendId(user.getId(), userFriendDto.getFriendId());

            if (userFriends == null) {
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

    @PutMapping("/{friendId}/friends/status")
    public ResponseEntity<Object> updateUserFriendStatus(@PathVariable("friendId") Long friendId,
                                                         @Valid @RequestBody UserFriendDto userFriendDto,
                                                         Authentication authentication) {
        try {
            String bearerName = authentication.getName();
            User friend = userService.findById(friendId);
            User bearerUser = userService.findByUsername(bearerName);

            if (userFriendDto == null) {
                return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
            }
            if (friendsService.findFriendByUserAndFriendId(bearerUser.getId(), friendId) == null) {
                return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
            }

            if (friend != null) {
                Friends updatedFriend = friendsService.updateFriendStatus(FriendsStatus.valueOf(userFriendDto.getFriendStatus()), bearerUser, friend.getId());
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


