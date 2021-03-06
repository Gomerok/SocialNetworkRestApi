package by.lwo.ukis.service;

import by.lwo.ukis.dto.UserDto;
import by.lwo.ukis.dto.UserRegistrationDto;
import by.lwo.ukis.model.User;
import by.lwo.ukis.model.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    User register(UserRegistrationDto userRegistrationDto);

    List<User> getAll();

    User findByUsername(String username);

    User findById(Long id);

    void delete(User user);

    User update(UserDto userDto);

    User updateStatus(Status status, User user);

    Page<User> findAllUserWithSearchParamPagination(String param, Pageable pageable);
}
