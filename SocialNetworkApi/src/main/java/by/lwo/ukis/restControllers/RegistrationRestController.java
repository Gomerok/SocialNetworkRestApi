package by.lwo.ukis.restControllers;

import by.lwo.ukis.dto.UserDto;
import by.lwo.ukis.dto.UserRegistrationDto;
import by.lwo.ukis.model.User;
import by.lwo.ukis.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/registration")
public class RegistrationRestController {

    private final UserService userService;

    @Autowired
    public RegistrationRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/")
    public ResponseEntity<Object> registry(@Valid @RequestBody UserRegistrationDto userRegistrationDto) {
        try{
            User findUser = userService.findByUsername(userRegistrationDto.getUsername());
            if (findUser == null) {
                User savedUser = userService.register(userRegistrationDto);
                UserDto result = UserDto.fromUser(savedUser);
                return new ResponseEntity<Object>(result, HttpStatus.OK);
            } else {
                return new ResponseEntity<Object>("User with username: "+userRegistrationDto.getUsername()+" exist",HttpStatus.FOUND);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
        }
    }
}
