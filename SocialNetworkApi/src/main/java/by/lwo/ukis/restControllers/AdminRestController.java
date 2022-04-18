package by.lwo.ukis.restControllers;

import by.lwo.ukis.dto.AdminUserDto;
import by.lwo.ukis.dto.UserDto;
import by.lwo.ukis.dto.UserRegistrationDto;
import by.lwo.ukis.model.User;
import by.lwo.ukis.model.enums.Status;
import by.lwo.ukis.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/admin")
public class AdminRestController {

    private final UserService userService;

    public AdminRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/users/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable(name = "id") Long id) {
        try {
            User user = userService.findById(id);

            if (user == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            AdminUserDto result = AdminUserDto.fromUser(user);

            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/users")
    public ResponseEntity<Object> getAllUsersSearchParamPagination(@RequestParam(name = "param", required = false, defaultValue = "") String param,
                                                                   @RequestParam(name = "pageNo", required = false, defaultValue = "0") Integer pageNo,
                                                                   @RequestParam(name = "pageSize", required = false, defaultValue = "2") Integer pageSize) {
        try {
            Pageable pageable = PageRequest.of(pageNo, pageSize);
            Page<User> userPage = userService.findAllUserWithSearchParamPagination(param, pageable);

            int totalElements = (int) userPage.getTotalElements();
            Page<AdminUserDto> adminUserDtoPage = new PageImpl<AdminUserDto>(userPage.getContent()
                    .stream()
                    .map(user -> AdminUserDto.fromUser(user))
                    .collect(Collectors.toList()), pageable, totalElements);

            List<AdminUserDto> resultList = adminUserDtoPage.getContent();

            if (resultList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<Object>(resultList, HttpStatus.OK);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/users")
    public ResponseEntity<Object> createUser(@Valid @RequestBody UserRegistrationDto userRegistrationDto) {
        try {
            User findUser = userService.findByUsername(userRegistrationDto.getUsername());
            if (findUser == null) {
                User savedUser = userService.register(userRegistrationDto);
                UserDto result = UserDto.fromUser(savedUser);
                return new ResponseEntity<Object>(result, HttpStatus.OK);
            } else {
                return new ResponseEntity<Object>("User with username: " + userRegistrationDto.getUsername() + "exist", HttpStatus.FOUND);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable("id") Long id, @Valid @RequestBody UserDto userDto) {
        try {
            User user = userService.findById(id);

            if (user != null) {
                if (userDto.getUsername().equals(user.getUsername())) {
                    return new ResponseEntity<Object>("User with username: " + user.getUsername() + " exist", HttpStatus.FOUND);
                }
                userDto.setId(user.getId());
                User updatedUser = userService.update(userDto);
                AdminUserDto result = AdminUserDto.fromUser(updatedUser);
                return new ResponseEntity<Object>(result, HttpStatus.OK);
            } else {
                return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") Long id) {
        try {

            User user = userService.findById(id);
            if (user != null) {
                userService.delete(user);
                return new ResponseEntity<HttpStatus>(HttpStatus.OK);
            } else {
                return new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/users/{id}/status")
    public ResponseEntity<Object> updateUserStatus(@PathVariable("id") Long id, @Valid @RequestBody AdminUserDto adminUserDto) {
        try {
            User user = userService.findById(id);

            if (user != null) {
                User updatedUser = userService.updateStatus(Status.valueOf(adminUserDto.getStatus()), user);
                AdminUserDto result = AdminUserDto.fromUser(updatedUser);
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
