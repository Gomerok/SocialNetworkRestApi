package by.lwo.ukis.dto;

import by.lwo.ukis.model.UserNews;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserNewsDto {
    private Long id;
    private String name;
    private String description;
    private Long userId;
    private String newsStatus;

    public static UserNewsDto fromUserNews(UserNews userNews) {
        UserNewsDto userNewsDto = new UserNewsDto();
        userNewsDto.setId(userNews.getId());
        userNewsDto.setName(userNews.getName());
        userNewsDto.setName(userNews.getName());
        userNewsDto.setDescription(userNews.getDescription());
        userNewsDto.setUserId(userNews.getUser().getId());
        userNewsDto.setNewsStatus(userNews.getNewsStatus().name());
        return userNewsDto;
    }

    public UserNews toUserNews(){
        UserNews userNews = new UserNews();
        userNews.setName(name);
        userNews.setDescription(description);
        return userNews;
    }
}
