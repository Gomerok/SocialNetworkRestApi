package by.lwo.ukis.dto;

import by.lwo.ukis.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRegistrationDto {
    @JsonIgnore
    private Long id;

    @NotBlank(message = "cannot be empty")
    @Pattern(regexp = "^[a-zA-Z]{3,20}$", message = "Incorrect username")
    private String username;
    @NotBlank(message = "cannot be empty")
    @Pattern(regexp = "^[a-zA-Z]{3,20}$", message = "Incorrect firstname")
    private String firstName;
    @Pattern(regexp = "^[a-zA-Z]{3,20}$", message = "Incorrect lastname")
    private String lastName;
    @JsonIgnore
    @NotBlank(message = "cannot be empty")
    @Pattern(regexp = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}", message = "Incorrect password")
    private String password;
    @NotBlank(message = "cannot be empty")
    @Email(message = "Incorrect email")
    private String email;

    public User toUser() {
        User user = new User();
        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);

        return user;
    }

    public static UserRegistrationDto fromUser(User user) {
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
        userRegistrationDto.setId(user.getId());
        userRegistrationDto.setUsername(user.getUsername());
        userRegistrationDto.setFirstName(user.getFirstName());
        userRegistrationDto.setLastName(user.getLastName());
        userRegistrationDto.setEmail(user.getEmail());

        return userRegistrationDto;
    }

    @JsonProperty("id")
    public Long getId() {
        return id;
    }
    @JsonIgnore
    public void setId(Long id) {
        this.id = id;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty("password")
    public void setPassword(String password) {
        this.password = password;
    }
}
