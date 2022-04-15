package by.lwo.ukis.restControllers;

import by.lwo.ukis.dto.UserNewsDto;
import by.lwo.ukis.model.User;
import by.lwo.ukis.model.UserNews;
import by.lwo.ukis.model.enums.NewsStatus;
import by.lwo.ukis.service.UserNewsService;
import by.lwo.ukis.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(value = "/api/v1/users")
public class UserNewsRestController {

    private final UserService userService;
    private final UserNewsService userNewsService;

    @Autowired
    public UserNewsRestController(UserService userService, UserNewsService userNewsService) {
        this.userService = userService;
        this.userNewsService = userNewsService;
    }

    @GetMapping(value = "/{userId}/news")
    public ResponseEntity<Object> getAllUserNewsPagination(@PathVariable(name = "userId") Long userId,
                                                           @RequestParam(name = "pageNo", required = false, defaultValue = "0") String pageNo,
                                                           @RequestParam(name = "pageSize", required = false, defaultValue = "2") String pageSize,
                                                           Authentication authentication) {
        try {
            if (!NumberUtils.isNumber(pageNo) || !NumberUtils.isNumber(pageSize)) {
                return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
            }

            Pageable pageable = PageRequest.of(Integer.parseInt(pageNo), Integer.parseInt(pageSize));
            Page<UserNews> userNewsPage = userNewsService.findAllNews(pageable);

            int totalElements = (int) userNewsPage.getTotalElements();
            Page<UserNewsDto> userNewsDtoPage = new PageImpl<UserNewsDto>(userNewsPage.getContent()
                    .stream()
                    .map(news -> UserNewsDto.fromUserNews(news))
                    .collect(Collectors.toList()), pageable, totalElements);

            List<UserNewsDto> resultList = userNewsDtoPage.getContent();

            if (resultList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<Object>(resultList, HttpStatus.OK);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/news/{id}")
    public ResponseEntity<Object> getUserNewsById(@PathVariable(name = "id") Long id) {
        try {
            UserNews userNews = userNewsService.findUserNewsById(id);

            if (userNews == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            UserNewsDto result = UserNewsDto.fromUserNews(userNews);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/{userId}/news")
    public ResponseEntity<Object> saveUserNews(@PathVariable(name = "userId") Long userId,
                                               @Valid @RequestBody UserNewsDto userNewsDto, Authentication authentication) {
        try {
            String bearerName = authentication.getName();
            User bearer = userService.findByUsername(bearerName);
            userNewsDto.setUserId(bearer.getId());

            if (!bearer.getId().equals(userId)) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

            UserNews savedUserNews = userNewsService.saveUserNews(userNewsDto);
            UserNewsDto result = UserNewsDto.fromUserNews(savedUserNews);
            return new ResponseEntity<Object>(result, HttpStatus.OK);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/news/{id}")
    public ResponseEntity<Object> updateUserNews(@PathVariable(name = "id") Long id,
                                                 @Valid @RequestBody UserNewsDto userNewsDto, Authentication authentication) {
        try {
            String bearerName = authentication.getName();
            User bearer = userService.findByUsername(bearerName);

            if (!bearer.getId().equals(userNewsDto.getUserId())) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

            UserNews userNews = userNewsService.findUserNewsById(id);
            if (userNews != null) {
                userNewsDto.setId(id);
                UserNews updatedUserNews = userNewsService.updateUserNews(userNewsDto);
                UserNewsDto result = UserNewsDto.fromUserNews(updatedUserNews);
                return new ResponseEntity<Object>(result, HttpStatus.OK);
            } else {
                return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/news/{id}/status")
    public ResponseEntity<Object> updateUserMessageStatus(@PathVariable("id") Long id, @Valid @RequestBody UserNewsDto userNewsDto) {
        try {
            UserNews userNews = userNewsService.findUserNewsById(id);
            System.out.println(userNews);
            if (userNews != null) {
                UserNews updatedUserNews = userNewsService.updateUserNewsStatus(NewsStatus.valueOf(userNewsDto.getNewsStatus()), userNews);
                UserNewsDto result = UserNewsDto.fromUserNews(updatedUserNews);
                return new ResponseEntity<Object>(result, HttpStatus.OK);
            } else {
                return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/news/{id}")
    public ResponseEntity<HttpStatus> deleteUserNews(@PathVariable("id") Long id) {
        try {
            UserNews userNews = userNewsService.findUserNewsById(id);
            if (userNews != null) {
                userNewsService.deleteUserNews(userNews);
                return new ResponseEntity<HttpStatus>(HttpStatus.OK);
            } else {
                return new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
        }
    }
}
