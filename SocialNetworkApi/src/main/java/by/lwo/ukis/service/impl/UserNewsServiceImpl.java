package by.lwo.ukis.service.impl;

import by.lwo.ukis.dto.UserNewsDto;
import by.lwo.ukis.model.UserNews;
import by.lwo.ukis.model.enums.NewsStatus;
import by.lwo.ukis.repository.UserNewsRepository;
import by.lwo.ukis.service.UserNewsService;
import by.lwo.ukis.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class UserNewsServiceImpl implements UserNewsService {

    private final UserService userService;
    private final UserNewsRepository userNewsRepository;

    @Autowired
    public UserNewsServiceImpl(UserNewsRepository userNewsRepository, UserService userService) {
        this.userNewsRepository = userNewsRepository;
        this.userService = userService;
    }

    @Override
    public Page<UserNews> findAllNews(Pageable pageable) {
        Page<UserNews> result = userNewsRepository.findAll(pageable);
        log.info("IN findAllNews - found {} page", result.getTotalElements());
        return result;
    }

    @Override
    public Page<UserNews> findAllNewsByUserId(Long userId, Pageable pageable) {
        Page<UserNews> result = userNewsRepository.findAllNewsByUserId(userId, pageable);
        log.info("IN findAllNewsByUserId - found {} page", result.getTotalElements());
        return result;
    }

    @Override
    public UserNews saveUserNews(UserNewsDto userNewsDto) {
        UserNews userNews = userNewsDto.toUserNews();

        userNews.setUser(userService.findById(userNewsDto.getUserId()));
        userNews.setNewsStatus(NewsStatus.CREATED);

        UserNews savedUserNews = userNewsRepository.save(userNews);
        log.info("IN saveUserNews - news: {} successfully saved", savedUserNews);
        return savedUserNews;
    }

    @Override
    public UserNews findUserNewsById(Long id) {
        UserNews result = userNewsRepository.findById(id).orElse(null);

        if (result == null) {
            log.warn("IN findUserNewsById - no news found by id: {}", id);
            return null;
        }

        log.info("IN findUserNewsById - news: {} found by id: {}", result, id);
        return result;
    }

    @Override
    public UserNews updateUserNews(UserNewsDto userNewsDto) {
        UserNews updatedUserNews = findUserNewsById(userNewsDto.getId());
        if (userNewsDto.getName() != null) updatedUserNews.setName(userNewsDto.getName());
        if (userNewsDto.getDescription() != null) updatedUserNews.setDescription(userNewsDto.getDescription());
        updatedUserNews.setNewsStatus(NewsStatus.UPDATED);
        UserNews result = userNewsRepository.save(updatedUserNews);
        log.info("IN updateUserNews - news with id: {} successfully update", result.getId());
        return result;
    }

    @Override
    public UserNews updateUserNewsStatus(NewsStatus status, UserNews userNews) {
        userNews.setNewsStatus(status);
        UserNews result = userNewsRepository.save(userNews);
        log.info("IN updateUserNewsStatus - news with id: {} successfully update status '{}' ", result.getId(), status.name());
        return result;
    }

    @Override
    public void deleteUserNews(UserNews userNews) {
        userNews.setNewsStatus(NewsStatus.DELETED);
        UserNews result = userNewsRepository.save(userNews);
        log.info("IN deleteUserNews - news with id: {} successfully deleted", result.getId());
    }

}
