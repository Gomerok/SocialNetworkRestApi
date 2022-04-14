package by.lwo.ukis.service;

import by.lwo.ukis.dto.UserDto;
import by.lwo.ukis.model.UserMessages;
import by.lwo.ukis.model.enums.Status;
import by.lwo.ukis.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    User register(UserDto userDto);

    List<User> getAll();

    User findByUsername(String username);

    User findById(Long id);

    void delete(User user);

    User update(UserDto userDto);

    User updateStatus(Status status, User user);

    Page<User> findAllUserWithSearchParamPagination(String param, Pageable pageable);
}
