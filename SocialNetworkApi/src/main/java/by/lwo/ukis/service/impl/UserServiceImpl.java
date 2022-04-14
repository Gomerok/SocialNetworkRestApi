package by.lwo.ukis.service.impl;

import by.lwo.ukis.dto.UserDto;
import by.lwo.ukis.dto.UserRegistrationDto;
import by.lwo.ukis.model.*;
import by.lwo.ukis.model.enums.Status;
import by.lwo.ukis.repository.RoleRepository;
import by.lwo.ukis.repository.UserRepository;
import by.lwo.ukis.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(UserRegistrationDto userRegistrationDto) {
        User user = userRegistrationDto.toUser();
        Role roleUser = roleRepository.findByName("ROLE_USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);

        user.setPassword(passwordEncoder.encode(userRegistrationDto.getPassword()));
        user.setRoles(userRoles);
        user.setStatus(Status.ACTIVE);

        User registeredUser = userRepository.save(user);

        log.info("IN register - user: {} successfully registered", registeredUser);

        return registeredUser;
    }

    @Override
    public List<User> getAll() {
        List<User> result = userRepository.findAll();
        log.info("IN getAll - {} users found", result.size());
        return result;
    }

    @Override
    public User findByUsername(String username) {
        User result = userRepository.findByUsername(username);
        log.info("IN findByUsername - user: {} found by username: {}", result, username);
        return result;
    }

    @Override
    public User findById(Long id) {
        User result = userRepository.findById(id).orElse(null);

        if (result == null) {
            log.warn("IN findById - no user found by id: {}", id);
            return null;
        }

        log.info("IN findById - user: {} found by id: {}", result, id);
        return result;
    }

    @Override
    public void delete(User user) {
        user.setStatus(Status.DELETED);
        User result = userRepository.save(user);
        log.info("IN delete - user with id: {} successfully deleted", result);
    }

    @Override
    public User update(UserDto updatedUser) {

        User oldUser = findById(updatedUser.getId());
        if (updatedUser.getUsername() != null) oldUser.setUsername(updatedUser.getUsername());
        if (updatedUser.getFirstName() != null) oldUser.setFirstName(updatedUser.getFirstName());
        if (updatedUser.getLastName() != null) oldUser.setLastName(updatedUser.getLastName());

        User result = userRepository.save(oldUser);
        log.info("IN update - user with id: {} successfully update", result);
        return result;
    }

    @Override
    public User updateStatus(Status status, User user) {
        user.setStatus(status);
        User result = userRepository.save(user);
        log.info("IN updateStatus - user with id: {} successfully update status '{}' ", result, status);
        return result;
    }

    @Override
    public Page<User> findAllUserWithSearchParamPagination(String param, Pageable pageable) {
        Page<User> result = userRepository.findAllWhitSearchParamPagination(param ,pageable);
        log.info("IN findAllUserWithSearchParamPagination - found {} users with search param {}", result.getTotalElements(), param);
        return result;
    }
}
