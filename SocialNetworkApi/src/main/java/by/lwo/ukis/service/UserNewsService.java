package by.lwo.ukis.service;

import by.lwo.ukis.dto.UserNewsDto;
import by.lwo.ukis.model.UserNews;
import by.lwo.ukis.model.enums.NewsStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserNewsService {
    Page<UserNews> findAllNews(Pageable pageable);

    Page<UserNews> findAllNewsByUserId(Long userId, Pageable pageable);

    UserNews saveUserNews(UserNewsDto userNewsDto);

    UserNews findUserNewsById(Long id);

    UserNews updateUserNews(UserNewsDto userNewsDto);

    UserNews updateUserNewsStatus(NewsStatus status, UserNews userNews);

    void deleteUserNews(UserNews userNews);
}
