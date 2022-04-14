package by.lwo.ukis.dto;

import by.lwo.ukis.model.enums.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import by.lwo.ukis.model.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdminUserDto {
    @JsonIgnore
    private Long id;

    @Pattern(regexp = "^[a-zA-Z]{3,20}$", message = "Incorrect username")
    private String username;
    @Pattern(regexp = "^[a-zA-Z]{3,20}$", message = "Incorrect firstname")
    private String firstName;
    @Pattern(regexp = "^[a-zA-Z]{3,20}$", message = "Incorrect lastname")
    private String lastName;
    @JsonIgnore
    @Pattern(regexp = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}", message = "Incorrect password")
    private String password;
    @Email(message = "Incorrect email")
    private String email;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date created;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date updated;
    private String status;

    public User toUser() {
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setCreated(created);
        user.setUpdated(updated);
        user.setStatus(Status.valueOf(status));
        return user;
    }

    public static AdminUserDto fromUser(User user) {
        AdminUserDto adminUserDto = new AdminUserDto();
        adminUserDto.setId(user.getId());
        adminUserDto.setUsername(user.getUsername());
        adminUserDto.setFirstName(user.getFirstName());
        adminUserDto.setLastName(user.getLastName());
        adminUserDto.setEmail(user.getEmail());
        adminUserDto.setCreated(user.getCreated());
        adminUserDto.setUpdated(user.getUpdated());
        adminUserDto.setStatus(user.getStatus().name());
        return adminUserDto;
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
