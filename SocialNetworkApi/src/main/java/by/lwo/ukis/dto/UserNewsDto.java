package by.lwo.ukis.dto;

import by.lwo.ukis.model.UserNews;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserNewsDto {

    @JsonIgnore
    private Long id;

    @Pattern(regexp = "^[a-zA-Z0-9,.()!? ]{3,50}$", message = "Incorrect name")
    private String name;
    @Pattern(regexp = "^[a-zA-Z0-9,.()!? ]{3,500}$", message = "Incorrect description")
    private String description;
    private Long userId;
    @Pattern(regexp = "^(CREATED|DELETED|UPDATED)$", message = "Incorrect newsStatus")
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

    @JsonProperty("id")
    public Long getId() {
        return id;
    }
    @JsonIgnore
    public void setId(Long id) {
        this.id = id;
    }
}
