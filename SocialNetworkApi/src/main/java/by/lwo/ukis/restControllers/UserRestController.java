package by.lwo.ukis.restControllers;

import by.lwo.ukis.dto.AdminUserDto;
import by.lwo.ukis.dto.UserDto;
import by.lwo.ukis.model.User;
import by.lwo.ukis.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/users")
public class UserRestController {

    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable(name = "id") Long id) {
        try {
            User user = userService.findById(id);

            if (user == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            UserDto result = UserDto.fromUser(user);

            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/")
    public ResponseEntity<Object> getAllUsersSearchParamPagination(@RequestParam(name = "param", required = false, defaultValue = "") String param,
                                                                   @RequestParam(name = "pageNo", required = false, defaultValue = "0") Integer pageNo,
                                                                   @RequestParam(name = "pageSize", required = false, defaultValue = "2") Integer pageSize) {
        try {
            Pageable pageable = PageRequest.of(pageNo, pageSize);
            Page<User> userPage = userService.findAllUserWithSearchParamPagination(param, pageable);

            int totalElements = (int) userPage.getTotalElements();
            Page<UserDto> userDtoPage = new PageImpl<UserDto>(userPage.getContent()
                    .stream()
                    .map(user -> UserDto.fromUser(user))
                    .collect(Collectors.toList()), pageable, totalElements);

            List<UserDto> resultList = userDtoPage.getContent();

            if (resultList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<Object>(resultList, HttpStatus.OK);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable("id") Long id, @Valid @RequestBody UserDto userDto, Authentication authentication) {
        try {
            User user = userService.findById(id);
            User bearer = userService.findByUsername(authentication.getName());

            if (user != null) {
                if (bearer.getId().equals(id)) {
                    userDto.setId(user.getId());
                    User updatedUser = userService.update(userDto);
                    UserDto result = UserDto.fromUser(updatedUser);
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
}
